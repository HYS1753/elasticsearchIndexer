package elasticsearch.persistence.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PobInfoId implements Serializable {
    private static final long serialVersionUID = -4388916263701196300L;
    @Column(name = "rgst_date", nullable = false, length = 8)
    private String rgstDate;

    @Column(name = "rgst_seq", nullable = false, precision = 7)
    private BigDecimal rgstSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PobInfoId entity = (PobInfoId) o;
        return Objects.equals(this.rgstDate, entity.rgstDate) &&
                Objects.equals(this.rgstSeq, entity.rgstSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rgstDate, rgstSeq);
    }

}
