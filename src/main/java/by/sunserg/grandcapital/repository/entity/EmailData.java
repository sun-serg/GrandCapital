package by.sunserg.grandcapital.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "\"EMAIL_DATA\"")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"USER_ID\"", referencedColumnName = "\"ID\"")
    private User user;

    @Column(name = "\"EMAIL\"")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailData emailData)) return false;
        return Objects.equals(user, emailData.user) && Objects.equals(email, emailData.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, email);
    }

    @Override
    public String toString() {
        return "EmailData{" +
                "id=" + id +
                ", user=" + user +
                ", email='" + email + '\'' +
                '}';
    }
}
