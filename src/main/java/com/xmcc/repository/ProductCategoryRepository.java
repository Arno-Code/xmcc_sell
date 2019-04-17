package com.xmcc.repository;

import com.xmcc.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


//泛型：第一个参数 是实体类名称  第二个参数是主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    //根据类型列表查询 集合
    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);


    /**
     * 自定义sql查询test
     * @param id
     * @param type
     * @return
     */
    //?1表示第一个参数 ?2表示第二个参数  nativeQuery表示用sql语句查  jpa默认是jpql  用sql语句必须用数据库的表名与字段名
    //@Query(value = "select category_name from product_category where category_id=?1 and category_type=?2",nativeQuery = true)
    //使用param必须所有参数都使用
    @Query(value = "select category_name from product_category where category_id=:categoryId and category_type=:categoryType",nativeQuery = true)
    String findNameByIdAndType(@Param("categoryId") Integer id,@Param("categoryType") Integer type);
}
