package seg.work.carog.server.common.entity;

import jakarta.persistence.Column;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "delete_yn", nullable = false)
    private String deleteYn;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }
}
