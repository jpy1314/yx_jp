package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheHashAspect {
    @Autowired
    private RedisTemplate redisTemplate;


    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){
        System.out.println("进入环绕通知");
        StringBuilder sb = new StringBuilder();
        //获取类的全路径
        String className = joinPoint.getTarget().getClass().getName();
        System.out.println(className);
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println(methodName);
        sb.append(className).append(methodName);
        //实参的值
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println(arg);
            sb.append(arg);
        }


        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object obj=null;
        //如果redis中有数据 和数据库查出来的
        if (hashOperations.hasKey(className, sb.toString())){

            //如果有这个key
            obj = hashOperations.get(className,sb.toString());
        }else {
            //如果没有这个key
            try {
                obj = joinPoint.proceed();//放行请求
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
           hashOperations.put(className, sb.toString(), obj);
        }

        System.out.println(sb);
        return obj;//这个数据会返回contrpller

    }
    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public  void delCache(JoinPoint joinPoint){
//        System.out.println("11111");
        System.out.println("后置通知");
        /*清除缓存

        */
        //类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(className);

    }

}
