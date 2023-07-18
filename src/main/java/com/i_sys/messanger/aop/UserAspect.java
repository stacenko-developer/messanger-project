package com.i_sys.messanger.aop;

import com.i_sys.messanger.dao.user.entity.User;
import com.i_sys.messanger.dao.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.UUID;

@Component
@Aspect
@AllArgsConstructor
public class UserAspect {

    private final UserRepository userRepository;
    @Around("com.i_sys.messanger.aop.UserPointcut.checkUserExistence()")
    public Object checkUserExistence(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        int index = Arrays.asList(paramNames).indexOf("userId");

        if (index != -1 && paramValues[index] instanceof UUID
                && userRepository
                .findByExternalId((UUID) paramValues[index]).orElse(null) == null) {
            User user = new User();
            user.setExternalId((UUID) paramValues[index]);
            userRepository.save(user);
        }

        return joinPoint.proceed();
    }
}
