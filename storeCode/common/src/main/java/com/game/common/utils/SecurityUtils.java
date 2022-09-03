package com.game.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.game.common.constant.HttpStatus;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.exception.ServiceException;

/**
 * Security service tools
 *
 * @author Yu Yue
 */
public class SecurityUtils
{
    /**
     * UserID
     **/
    public static Long getUserId()
    {
        try
        {
            return getLoginUser().getUserId();
        }
        catch (Exception e)
        {
            throw new ServiceException("Get UserID Exception", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get department ID
     **/
    public static Long getDeptId()
    {
        try
        {
            return getLoginUser().getDeptId();
        }
        catch (Exception e)
        {
            throw new ServiceException("Get DepartmentID exception", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get User account
     **/
    public static String getUsername()
    {
        try
        {
            return getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            throw new ServiceException("Get User account exception,Again Login", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * get User
     **/
    public static LoginUser getLoginUser()
    {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new ServiceException("Get User Information exception", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Generate BCryptPasswordEncoder password
     *
     * @param password password
     * @return encrypted string
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * Determine if the password is the same
     *
     * @param rawPassword real password
     * @param encodedPassword encrypted character
     * @return result
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Are you an administrator
     *
     * @param userId UserID
     * @return result
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }
}
