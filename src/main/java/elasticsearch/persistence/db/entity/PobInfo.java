package elasticsearch.persistence.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "pob_info", schema = "public")
public class PobInfo {
    @EmbeddedId
    private PobInfoId id;

    @Column(name = "dgt5_pssr_num", length = 5)
    private String dgt5PssrNum;

    @Column(name = "sd_name", length = 20)
    private String sdName;

    @Column(name = "sgg_name", length = 20)
    private String sggName;

    @Column(name = "emd_name", length = 20)
    private String emdName;

    @Column(name = "pob_name", length = 80)
    private String pobName;

    @Column(name = "pob_sttg_orgn_nmbr", length = 8)
    private String pobSttgOrgnNmbr;

    @Column(name = "pob_sttg_addt_nmbr", length = 8)
    private String pobSttgAddtNmbr;

    @Column(name = "pob_end_orgn_nmbr", length = 8)
    private String pobEndOrgnNmbr;

    @Column(name = "pob_end_addt_nmbr", length = 8)
    private String pobEndAddtNmbr;

    @Column(name = "crtr_id", nullable = false, length = 100)
    private String crtrId;

    @Column(name = "cret_dttm", nullable = false)
    private Instant cretDttm;

    @Column(name = "amnr_id", nullable = false, length = 100)
    private String amnrId;

    @Column(name = "amnd_dttm", nullable = false)
    private Instant amndDttm;

    @ColumnDefault("'N'")
    @Column(name = "dlt_ysno", nullable = false, length = 1)
    private String dltYsno;

}