package apap.group.assignment.SIFACTORY.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "produksi")
public class ProduksiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduksi;

    @NotNull
    @Column(nullable = false)
    private String idItem;

    @NotNull
    @Column(nullable = false)
    private Integer idKategori;

    @NotNull
    @Column(nullable = false)
    private Integer tambahanStok;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggalProduksi;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pegawai", referencedColumnName = "idPegawai", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PegawaiModel pegawai;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_mesin", referencedColumnName = "idMesin", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MesinModel mesin;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_request_update_item", referencedColumnName = "idRequestUpdateItem", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RequestUpdateItemModel requestUpdateItem;

}
