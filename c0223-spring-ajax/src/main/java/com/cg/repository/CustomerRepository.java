package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.dto.customer.CustomerResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByEmailAndIdNot(String email, Long id);

    @Query("SELECT NEW com.cg.model.dto.customer.CustomerResDTO (" +
            "cus.id," +
            "cus.fullName," +
            "cus.email," +
            "cus.phone," +
            "cus.balance, " +
            "cus.locationRegion" +
            ") " +
            "FROM Customer as cus " +
            "WHERE cus.deleted = false ")
    List<CustomerResDTO> findAllCustomerResDTO();

//    @Query("UPDATE Customer  AS cus SET " +
//            "cus.fullName = :fullName," +
//            "cus.email = :email ," +
//            "cus.phone = :phone," +
//            "cus.locationRegion.districtId = :districtId," +
//            "cus.locationRegion.districtName = :districtName," +
//            "cus.locationRegion.provinceId = :provinceId," +
//            "cus.locationRegion.provinceName = :provinceNAme," +
//            "cus.locationRegion.wardId = :wardId," +
//            "cus.locationRegion.wardName = :wardName," +
//            "cus.locationRegion.address = :address " +
//            "WHERE cus.id = :id")
//    void update(@Param("id") Long id,
//                @Param("fullName") String fullName,
//                @Param("email") String email,
//                @Param("phone") String phone,
//                @Param("districtId") Long districtId,
//                @Param("districtName") String districtName,
//                @Param("provinceId") Long provinceId,
//                @Param("provinceName") String provinceName,
//                @Param("wardId") Long wardId,
//                @Param("wardName") String wardName,
//                @Param("address") String address);

    @Modifying
    @Query("UPDATE Customer AS cus SET cus.balance = cus.balance + :amount WHERE cus.id = :id")
    void incrementBalance(@Param("id") Long id, @Param("amount") BigDecimal amount);


    @Modifying
    @Query("UPDATE Customer AS cus SET cus.balance = cus.balance - :amount WHERE cus.id = :id")
    void decrementBalance(@Param("id") Long id, @Param("amount") BigDecimal amount);

    @Query("SELECT NEW com.cg.model.dto.customer.CustomerResDTO (" +
            "cus.id, " +
            "cus.fullName, " +
            "cus.email," +
            "cus.phone," +
            "cus.balance, " +
            "cus.locationRegion" +
            ") " +
            "FROM Customer AS cus " +
            "WHERE cus.deleted = false " +
            "AND cus.id <> :senderId "
    )
    List<CustomerResDTO> findAllRecipientsWithoutSenderId(@Param("senderId") Long senderId);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c " +
            "SET c.deleted = TRUE " +
            "WHERE c.id = :id")
    void deleteByIdTrue(@Param("id") long id);

    @Query("SELECT NEW com.cg.model.dto.customer.CustomerResDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.email, " +
            "c.phone, " +
            "c.balance, " +
            "c.locationRegion " +
            ") " +
            "FROM Customer c " +
            "WHERE c.id = :id AND c.deleted = false")
    Optional<CustomerResDTO> findCustomerDTOById(@Param("id") Long id);
}
