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
@Table(name = "request_update_item")
public class RequestUpdateItemModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRequestUpdateItem;

    @Column(nullable = false)
    private String idItem;

    @Column(nullable = false)
    private Integer idKategori;

    @NotNull
    @Column(nullable = false)
    private Integer tambahanStok;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggalRequest;

    @NotNull
    @Column(nullable = false)
    private Integer idCabang;

    @NotNull
    @Column(nullable = false)
    private Boolean executed;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
//    @JoinColumn(name = "id_delivery", referencedColumnName = "idDelivery", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DeliveryModel delivery;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
//    @JoinColumn(name = "id_produksi", referencedColumnName = "idProduksi", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProduksiModel produksi;
}
