package me.riguron.telegram.repository;

import me.riguron.telegram.entity.task.DumpTaskRecord;
import me.riguron.telegram.projection.TaskProjection;
import me.riguron.telegram.entity.task.DumpTaskRecord;
import me.riguron.telegram.projection.TaskProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DumpTaskRepository extends JpaRepository<DumpTaskRecord, Long> {

    @Query("select " +
            "new me.riguron.telegram.projection.TaskProjection" +
            "(t.date, o.channel, o.images, o.type, t.file, s.description, t.completed)" +
            "from DumpTaskRecord t " +
            "JOIN t.dumpTaskOptions o " +
            "JOIN t.dumpTaskState s " +
            "JOIN t.userProfile p " +
            "WHERE p.id = :userId"
    )
    List<TaskProjection> getTaskHistory(@Param("userId") int userId, Sort sort);
}
