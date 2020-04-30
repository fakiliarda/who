package com.yaf.who.dao.iml;

import com.yaf.who.dao.UserDAO;
import com.yaf.who.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ardafakili
 * @date 23.04.2020
 */

@Repository
public class UserProfileDataAccessService implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserProfileDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public UserProfile insertUserProfile(UserProfile userProfile) {
        final String sql = "INSERT INTO userprofile (id, username, imagelink) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userProfile.getUserProfileId(), userProfile.getUsername(), userProfile.getUserImageLink());
            return userProfile;
        }

    @Override
    public List<UserProfile> selectAllUserProfiles() {
        final String sql = "SELECT * FROM userprofile";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String username = resultSet.getString("username");
            String imagelink = resultSet.getString("imagelink");
            return new UserProfile(id, username, imagelink);
        });
    }

    @Override
    public Optional<UserProfile> selectUserProfileById(UUID id) {
        final String sql = "SELECT * FROM userprofile WHERE id = ?";
        UserProfile userProfile = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
            UUID userId = UUID.fromString(resultSet.getString("id"));
            String username = resultSet.getString("username");
            return new UserProfile(userId, username, "");
        });

        return Optional.ofNullable(userProfile);
    }

    @Override
    public int deleteUserProfileWithById(UUID id) {
        return 0;
    }

    @Override
    public int updateUserProfileById(UUID id, UserProfile userProfile) {
        final String sql = "UPDATE userprofile SET username = ?, imagelink = ? WHERE id = ?";
        jdbcTemplate.update(sql, userProfile.getUsername(), userProfile.getUserImageLink(), id);
        return 1;
    }
}
