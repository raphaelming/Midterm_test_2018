import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest {
    private ProductDao productDao;
    private DaoFactory daoFactory;

    @Before
    public void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        productDao = applicationContext.getBean("productDao", ProductDao.class);
    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        Long id = 1L;
        String title = "제주감귤";
        Integer price = 15000;

        Product product = productDao.get(id);
        assertEquals(id, product.getId());
        assertEquals(title, product.getTitle());
        assertEquals(price, product.getPrice());
    }

    @Test
    public void add() throws SQLException, ClassNotFoundException {
        Product product = new Product();
        Long id = insertProductTest(product);

        Product insertedProduct = productDao.get(id);
        assertEquals(id, insertedProduct.getId());
        assertEquals(product.getTitle(), insertedProduct.getTitle());
        assertEquals(product.getPrice(), insertedProduct.getPrice());
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        Product product = new Product();
        Long id = insertProductTest(product);
        product.setId(id);
        product.setTitle("apple");
        product.setPrice(5000);
        productDao.update(product);

        Product updatedProduct = productDao.get(id);
        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getTitle(), updatedProduct.getTitle());
        assertEquals(product.getPrice(), updatedProduct.getPrice());

    }

    private Long insertProductTest(Product product) throws ClassNotFoundException, SQLException {
        product.setTitle("orange");
        product.setPrice(20000);
        return productDao.insert(product);
    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        Product product = new Product();
        Long id = insertProductTest(product);

        productDao.delete(id);

        Product deletedProduct = productDao.get(id);
        assertEquals(null, deletedProduct);

    }

}
