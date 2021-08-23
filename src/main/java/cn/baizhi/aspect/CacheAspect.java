package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

//@Aspect
//@Component
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    /*
    下面这个是切面类中的环绕通知（额外功能）
    配置切点
    切点表达式
    excution();方法级别
    within();类级别
    @annotation();注解方式

     */
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

        //只要执行service中的查询，一定会执行这个环绕通知
        /*
        obj:这是当前目标方法执行后的结果
        redis: key value
        key ：类名全路径 + 方法名 + 实参
        value: obj
        */
//        System.out.println(obj);
        /*判断redis中已有缓存的数据 就从redis中拿 就不放行了
        sb.toString() : 当前的key
        */
        //取消键的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object obj=null;
        //如果redis中有数据 和数据库查出来的
        if (redisTemplate.hasKey(sb.toString())){
            //如果有这个key
            obj = valueOperations.get(sb.toString());
        }else {
            //如果没有这个key
            try {
                obj = joinPoint.proceed();//放行请求
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            valueOperations.set(sb.toString(), obj);
        }

        System.out.println(sb);
        return obj;//这个数据会返回contrpller
/*只要执行了增删改 就应该清除缓存
*
* 我们要想明白清除那些缓存
* 比如我们在用户模块：添加了一个用户，
* 意味着用户信息已经改变  对于用户模块所有查询都会收到影响
* 所哟如果用户模块发生改变 那么用户所有查询缓存都应该删除
*
* 开发一个额外功能 ：删除redis中的 缓存
* 切点：
* 通知： 前置 后置 异常 环绕*/
    }
    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public  void delCache(JoinPoint joinPoint){
//        System.out.println("11111");
        System.out.println("后置通知");
        /*清除缓存

        */
        //类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        System.out.println(className);
        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
           String newKey=(String)key;
           if (newKey.startsWith(className)){
               redisTemplate.delete(key);
           }
        }
    /*
    redis 中 hash 类型
    key     value
    大键
            key value
            key value
            key value

    包名 + 类名
            cn.baizhi.service.UserServiceImplqueryByPage33   object
            cn.baizhi.service.UserServiceImplqueryByPage23
            cn.baizhi.service.UserServiceImplqueryByPage13
     */
    }

}
