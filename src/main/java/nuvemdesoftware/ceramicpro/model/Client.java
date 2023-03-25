package nuvemdesoftware.ceramicpro.model;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long   seq_id;

    @Column(name = "client_id", nullable = false)
    private int  clientId;
    @Column(name = "client_name", nullable = false)
    private String  clientName;
    @Column(name = "first_address", nullable = false)
    private String  firstAddress;
    @Column(nullable = true)
    private String  location;
    @Column(nullable = true)
    private String  country;
    @Column(name = "country_code", nullable = false)
    private String  countryCode;
    @Column(name = "postal_code", nullable = false)
    private String  postalCode;
    @Column(nullable = true)
    private String  nif;
    @Column(nullable = true)
    private String  coin;
    @Column(name = "phone_number", nullable = false)
    private String  phoneNumber;
    @Column(name = "person_to_contact", nullable = false)
    private String  personToContact;
    @Column(name = "image_path", nullable = false)
    private String  imagePath;
    @Column(name = "image_name", nullable = false)
    private String  imageName;
    @Column(name="created_date", nullable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;
    @Column(name="modified_date", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date modifiedDate;

    public long getSeq_id() {
        return seq_id;
    }

    public void setSeq_id(long seq_id) {
        this.seq_id = seq_id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public void setFirstAddress(String firstAddress) {
        this.firstAddress = firstAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonToContact() {
        return personToContact;
    }

    public void setPersonToContact(String personToContact) {
        this.personToContact = personToContact;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "Client{" +
                "seq_id=" + seq_id +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", firstAddress='" + firstAddress + '\'' +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", nif='" + nif + '\'' +
                ", coin='" + coin + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", personToContact='" + personToContact + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageName='" + imageName + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
