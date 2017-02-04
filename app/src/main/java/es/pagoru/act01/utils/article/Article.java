package es.pagoru.act01.utils.article;

/**
 * Created by Pablo on 02/02/2017.
 */

public class Article {

    private String code;
    private String description;
    private int pvp;
    private int stock;

    public Article(String code){
        this.code = code;
    }

    public Article(
            String code,
            String description,
            int pvp,
            int stock) {
        this.code = code;
        this.description = description;
        this.pvp = pvp;
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getPvp() {
        return pvp;
    }

    public int getStock() {
        return stock;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPvp(int pvp) {
        this.pvp = pvp;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
