package entities;

import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pizza")
public class Pizza {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")                                in table value varchar!
    private UUID id;*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "COMMENTS")
    @Basic(fetch = FetchType.LAZY)
    private String comment;
    private double prize;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "pizza_components",
            joinColumns = {@JoinColumn(name = "pizza_id")},
            inverseJoinColumns = {@JoinColumn(name = "component_id")})
    private Set<Component> components;

    public Pizza() {
    }

    public Pizza(int id, String name, String comment, double prize, Set<Component> components) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.prize = prize;
        this.components = components;
    }

    @Override
    public String toString() {
        return "Pizza{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", comment='" + comment + '\''
                + ", prize=" + prize
                + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }
}
