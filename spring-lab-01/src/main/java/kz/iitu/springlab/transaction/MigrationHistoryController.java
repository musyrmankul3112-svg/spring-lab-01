package kz.iitu.springlab.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/migrations")
public class MigrationHistoryController {

    private final JdbcTemplate jdbcTemplate;

    public MigrationHistoryController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/history")
    public List<Map<String, Object>> history() {
        return jdbcTemplate.queryForList("""
                SELECT "installed_rank", "version", "description", "success"
                FROM "flyway_schema_history"
                ORDER BY "installed_rank"
                """);
    }
}