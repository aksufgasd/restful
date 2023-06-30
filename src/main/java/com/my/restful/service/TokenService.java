package com.my.restful.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenService {

    // 一般是把token和用户对应关系放在数据库或高速缓存(例如readis/memcache等），放在一个单例类的成员变量里仅适合很小规模的情形
    private Map<String, UserDetails> tokenMap = new HashMap<>();

    /**
     * 这个方法在每次访问都会被调用；为了提示效率应该使用@Cacheable注解缓存
     */
    public UserDetails getUserFromToken(String token) {
        if(token == null) {
            return null;
        }
        return tokenMap.get(token);
    }

    /**
     * 登录，成功返回token
     * @param userName
     * @param password
     * @return
     */
    public String login(String userName, String password) {
        UserDetails ud = null;
        // 此例中支持三个用户： author/reader/admin 三个用户的密码都是chen; author具有author角色；rader具有reader角色；admin则2个角色都有
        if("author".equals(userName) && "chen".equals(password)) {
            ud = createUser(userName, password, new String[] {"author"});
        }else if("reader".equals(userName) && "chen".equals(password)) {
            ud = createUser(userName, password, new String[] {"reader"});
        }else if("admin".equals(userName) && "chen".equals(password)) {
            ud = createUser(userName, password, new String[] {"reader", "author"});
        }
        if(ud != null) {
            String token = UUID.randomUUID().toString();
            tokenMap.put(token, ud);
            return token;
        }else {
            return null;
        }
    }

    /**
     * 退出，移除token
     * @param token
     */
    public void logout(String token) {
        tokenMap.remove(token);
    }


    private UserDetails createUser(String userName, String password, String[] roles) {
        final String[] role = roles;
        final String user = userName , pass = password;

        return new UserDetails() {

            private static final long serialVersionUID = 6905138725952656074L;

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

                //这是增加了一种名为query的权限，可以使用 @hasAuthority("query") 来判断
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("query");
                authorities.add(authority);

                //这是增加到xxx角色，可以用hasRole("xxx")来判断；需要注意所有的角色在这里增加时必须以ROLE_前缀，使用时则没有ROLES_前缀
                for(String role : role) {
                    SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_" + role);
                    authorities.add(sga);
                }
                return authorities;
            }

            @Override
            public String getPassword() {
                return pass;
            }

            @Override
            public String getUsername() {
                return user;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

        };
    }

}
