package nuvemdesoftware.ceramicpro.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name="products_technical_sheet")
public class ProductsTechnicalSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq_id;

    @Column(name = "customer_product_id", nullable = false)
    private String customerProductId;
    @Column(name = "internal_product_id", nullable = false)
    private String internalProductId;
    @Column(name = "raw_material", nullable = true)
    private String rawMaterial;
    @Column(name = "raw_material_extra", nullable = true)
    private String rawMaterialExtra;
    @Column(name = "dimensions_in_wet", nullable = true)
    private String dimensionsInWet;
    @Column(name = "product_weight", nullable = true)
    private String productWeight;
    @Column(name = "product_height", nullable = true)
    private String productHeight;
    @Column(name = "product_width", nullable = true)
    private String productWidth;
    @Column(name = "top_width", nullable = true)
    private String topWidth;
    @Column(name = "bottom_width", nullable = true)
    private String bottomWidth;
    @Column(name = "relief", nullable = true)
    private String relief;
    @Column(name = "sponge", nullable = true)
    private String sponge;
    @Column(name = "cooking", nullable = true)
    private String cooking;
    @Column(name = "cooking_temperature", nullable = true)
    private String cookingTemperature;
    @Column(name = "production_observations", nullable = true)
    private String productionObservations;
    @Column(name = "painted_cold", nullable = true)
    private String paintedCold;
    @Column(name = "ref_paint", nullable = true)
    private String refPaint;
    @Column(name = "ref_paint_qty", nullable = true)
    private String refPaintQty;
    @Column(name = "glassed", nullable = true)
    private String glassed;
    @Column(name = "ref_glassed", nullable = true)
    private String refGlassed;
    @Column(name = "ref_paint_smoked", nullable = true)
    private String refPaintSmoked;
    @Column(name = "ref_paint_smoked_qty", nullable = true)
    private String refPaintSmokedQty;
    @Column(name = "finish_type_obs", nullable = true)
    private String finishTypeObs;
    @Column(name = "bar_code_tech_sheet", nullable = true)
    private String barCodeTechSheet;
    @Column(name = "label_water_proof", nullable = true)
    private String labelWaterProof;
    @Column(name = "felts", nullable = true)
    private String felts;
    @Column(name = "felts_qty", nullable = true)
    private String feltsQty;
    @Column(name = "bag", nullable = true)
    private String bag;
    @Column(name = "bag_size", nullable = true)
    private String bagSize;
    @Column(name = "qty_by_box", nullable = true)
    private String qtyByBox;
    @Column(name = "box_measures", nullable = true)
    private String boxMeasures;
    @Column(name = "box_id", nullable = true)
    private String boxId;
    @Column(name = "disposition_by_row", nullable = true)
    private String dispositionByRow;
    @Column(name = "qty_by_pallet", nullable = true)
    private String qtyByPallet;
    @Column(name = "qty_by_pallet_compound_product", nullable = true)
    private String qtyByPalletCompoundProduct;
    @Column(name = "final_observations", nullable = true)
    private String finalObservations;

    @Column(name="created_date", nullable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;
    @Column(name="modified_date", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date modifiedDate;

}
