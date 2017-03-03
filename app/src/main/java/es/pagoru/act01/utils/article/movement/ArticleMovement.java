package es.pagoru.act01.utils.article.movement;

import java.util.Calendar;

/**
 * Created by Pablo on 04/02/2017.
 */

public class ArticleMovement {

    private Calendar calendar;
    private int quantity;
    private MovementType movementType;

    public ArticleMovement(
            Calendar calendar,
            int quantity,
            MovementType movementType) {
        this.calendar = calendar;
        this.quantity = quantity;
        this.movementType = movementType;
    }


    public Calendar getCalendar() {
        return calendar;
    }

    public int getQuantity() {
        return quantity;
    }

    public MovementType getMovementType() {
        return movementType;
    }


    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }
}
