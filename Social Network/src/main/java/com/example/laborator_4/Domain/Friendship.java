package com.example.laborator_4.Domain;

import java.time.LocalDateTime;

public class Friendship extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;


    public Friendship() {
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }
    public void seDate(LocalDateTime dateTime){this.date = dateTime;}

    public String toString(){
        return "Prietenie{" +
                "id1='" + getId().getLeft() + '\'' +
                ", id2='" + getId().getRight() + '\'' +
                ", data=" + date +
                '}';
    }
}
