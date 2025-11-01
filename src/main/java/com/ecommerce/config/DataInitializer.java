package com.ecommerce.config;

import com.ecommerce.model.*;
import com.ecommerce.repositoty.OrderRepository;
import com.ecommerce.repositoty.ProductRepository;
import com.ecommerce.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataInitializer run method executed");

        // Verificar se os dados já existem antes de inserir
        if (productRepository.count() > 0) {
            System.out.println("Dados já existem no banco, pulando inicialização...");
            return;
        }

        System.out.println("Inicializando dados no banco...");

        // Insert de Produtos
        productRepository.saveAll(Arrays.asList(
                createProduct("Smartphone Pro Max", Category.ELECTRONICS, 2999.99, Product.ProductStatus.CREATED, "Smartphone com câmera de alta resolução e processador potente"),
                createProduct("Camiseta Premium", Category.CLOTHING, 89.90, Product.ProductStatus.CREATED, "Camiseta 100% algodão com design exclusivo"),
                createProduct("Notebook Gamer", Category.ELECTRONICS, 4599.99, Product.ProductStatus.CREATED, "Notebook para jogos com placa de vídeo dedicada"),
                createProduct("Cadeira Ergonômica", Category.HOME_GARDEN, 899.99, Product.ProductStatus.CREATED, "Cadeira de escritório com apoio lombar e ajuste de altura"),
                createProduct("Tênis Esportivo", Category.SPORTS, 299.99, Product.ProductStatus.CREATED, "Tênis para corrida com tecnologia de amortecimento"),
                createProduct("Fones Bluetooth", Category.ELECTRONICS, 599.99, Product.ProductStatus.CREATED, "Fones de ouvido sem fio com cancelamento de ruído"),
                createProduct("Tablet Ultra", Category.ELECTRONICS, 1899.00, Product.ProductStatus.CREATED, "Tablet de 12 polegadas com tela OLED e processador octa-core"),
                createProduct("Smart TV 55 polegadas", Category.ELECTRONICS, 3299.99, Product.ProductStatus.CREATED, "Smart TV 4K HDR com sistema Android integrado"),
                createProduct("Console PlayStation", Category.ELECTRONICS, 2699.90, Product.ProductStatus.CREATED, "Console de videogame de última geração com 1TB de armazenamento"),
                createProduct("Smartwatch Fitness", Category.ELECTRONICS, 899.00, Product.ProductStatus.CREATED, "Relógio inteligente com monitoramento de saúde 24h"),
                createProduct("Câmera Digital Profissional", Category.ELECTRONICS, 5999.99, Product.ProductStatus.CREATED, "Câmera DSLR 24MP com lente 18-55mm incluída"),
                createProduct("Home Theater 5.1", Category.ELECTRONICS, 1599.99, Product.ProductStatus.CREATED, "Sistema de som surround com subwoofer wireless"),
                createProduct("Drone com Câmera 4K", Category.ELECTRONICS, 2199.00, Product.ProductStatus.CREATED, "Drone profissional com estabilizador gimbal e GPS"),
                createProduct("Kindle E-reader", Category.ELECTRONICS, 399.99, Product.ProductStatus.CREATED, "Leitor de e-books com tela antirreflexo e bateria duradoura"),
                createProduct("Caixa de Som Portátil", Category.ELECTRONICS, 189.90, Product.ProductStatus.CREATED, "Alto-falante Bluetooth à prova de água com 20h de bateria"),
                createProduct("Jaqueta de Couro", Category.CLOTHING, 459.99, Product.ProductStatus.CREATED, "Jaqueta de couro legítimo com forro interno removível"),
                createProduct("Vestido Elegante", Category.CLOTHING, 299.90, Product.ProductStatus.CREATED, "Vestido midi em tecido fluido com estampa floral"),
                createProduct("Calça Jeans Premium", Category.CLOTHING, 199.99, Product.ProductStatus.CREATED, "Calça jeans slim fit com lavagem especial e stretch"),
                createProduct("Blazer Masculino", Category.CLOTHING, 349.00, Product.ProductStatus.CREATED, "Blazer social em tecido de alfaiataria com corte moderno"),
                createProduct("Bolsa de Couro Feminina", Category.CLOTHING, 279.99, Product.ProductStatus.CREATED, "Bolsa de mão em couro sintético com alças ajustáveis"),
                createProduct("Sapato Social Masculino", Category.CLOTHING, 259.90, Product.ProductStatus.CREATED, "Sapato de couro com solado antiderrapante e palmilha acolchoada"),
                createProduct("Blusa de Seda", Category.CLOTHING, 189.99, Product.ProductStatus.CREATED, "Blusa feminina em seda natural com estampa geométrica"),
                createProduct("Bermuda Cargo", Category.CLOTHING, 129.90, Product.ProductStatus.CREATED, "Bermuda masculina com múltiplos bolsos e tecido resistente"),
                createProduct("Óculos de Sol", Category.CLOTHING, 159.99, Product.ProductStatus.CREATED, "Óculos com proteção UV400 e armação em acetato"),
                createProduct("Carteira de Couro", Category.CLOTHING, 89.99, Product.ProductStatus.CREATED, "Carteira masculina em couro legítimo com porta-cartões"),
                createProduct("Sofá 3 Lugares", Category.HOME_GARDEN, 1899.99, Product.ProductStatus.CREATED, "Sofá em tecido suede com estrutura de madeira maciça"),
                createProduct("Mesa de Jantar", Category.HOME_GARDEN, 1299.00, Product.ProductStatus.CREATED, "Mesa redonda em madeira com tampo de vidro temperado"),
                createProduct("Luminária de Piso", Category.HOME_GARDEN, 299.99, Product.ProductStatus.CREATED, "Luminária moderna com dimmer e base em mármore"),
                createProduct("Conjunto de Panelas", Category.HOME_GARDEN, 459.90, Product.ProductStatus.CREATED, "Kit com 5 panelas antiaderentes e tampas de vidro"),
                createProduct("Aspirador Robô", Category.HOME_GARDEN, 1599.99, Product.ProductStatus.CREATED, "Aspirador inteligente com mapeamento e controle por app"),
                createProduct("Purificador de Ar", Category.HOME_GARDEN, 799.00, Product.ProductStatus.CREATED, "Purificador com filtro HEPA e ionizador integrado"),
                createProduct("Conjunto de Ferramentas", Category.HOME_GARDEN, 199.99, Product.ProductStatus.CREATED, "Kit completo com 50 peças e maleta organizadora"),
                createProduct("Vaso Decorativo Grande", Category.HOME_GARDEN, 149.90, Product.ProductStatus.CREATED, "Vaso em cerâmica esmaltada com design contemporâneo"),
                createProduct("Ventilador de Teto", Category.HOME_GARDEN, 349.99, Product.ProductStatus.CREATED, "Ventilador com 3 pás e controle remoto incluso"),
                createProduct("Tapete Persa", Category.HOME_GARDEN, 599.99, Product.ProductStatus.CREATED, "Tapete decorativo 2x3m com estampa tradicional"),
                createProduct("Bicicleta Mountain Bike", Category.SPORTS, 1899.00, Product.ProductStatus.CREATED, "Bike aro 29 com 21 marchas e suspensão dianteira"),
                createProduct("Esteira Elétrica", Category.SPORTS, 2499.99, Product.ProductStatus.CREATED, "Esteira dobrável com motor 2.5HP e inclinação automática"),
                createProduct("Kit Halteres Ajustáveis", Category.SPORTS, 899.90, Product.ProductStatus.CREATED, "Conjunto de halteres de 5kg a 25kg com suporte"),
                createProduct("Bola de Futebol Oficial", Category.SPORTS, 129.99, Product.ProductStatus.CREATED, "Bola oficial FIFA em couro sintético costurado à mão"),
                createProduct("Prancha de Surf", Category.SPORTS, 1299.00, Product.ProductStatus.CREATED, "Prancha longboard 9 pés em fibra de vidro"),
                createProduct("Raquete de Tênis", Category.SPORTS, 399.99, Product.ProductStatus.CREATED, "Raquete profissional com corda de alta tensão"),
                createProduct("Saco de Pancada", Category.SPORTS, 499.90, Product.ProductStatus.CREATED, "Saco de boxe 1,20m em couro sintético com enchimento"),
                createProduct("Patins In-line", Category.SPORTS, 259.99, Product.ProductStatus.CREATED, "Patins ajustáveis com rodas 80mm e sistema de freio"),
                createProduct("Boneca Interativa", Category.TOYS, 199.99, Product.ProductStatus.CREATED, "Boneca que fala 50 frases e vem com acessórios"),
                createProduct("Carrinho Controle Remoto", Category.TOYS, 299.90, Product.ProductStatus.CREATED, "Carro RC com bateria recarregável e alcance de 100m"),
                createProduct("LEGO Castelo Medieval", Category.TOYS, 459.99, Product.ProductStatus.CREATED, "Set com 1200 peças para construir castelo com personagens"),
                createProduct("Quebra-cabeça 2000 peças", Category.TOYS, 89.99, Product.ProductStatus.CREATED, "Puzzle com imagem de paisagem europeia"),
                createProduct("Pelúcia Gigante Urso", Category.TOYS, 189.90, Product.ProductStatus.CREATED, "Urso de pelúcia de 80cm em material antialérgico"),
                createProduct("Kit Experiências Científicas", Category.TOYS, 159.99, Product.ProductStatus.CREATED, "Kit educativo com 50 experimentos seguros para crianças"),
                createProduct("Jogo de Tabuleiro Estratégia", Category.TOYS, 129.99, Product.ProductStatus.CREATED, "Jogo para 2-6 jogadores com duração de 60-90 minutos")
        ));

        // Insert de Usuários
        User joao = createUser("João", "Silva", "joao@email.com", "(11) 98765-4321", "Abc@12345678", true, User.UserStatus.ACTIVE);
        User maria = createUser("Maria", "Santos", "maria@email.com", "(21) 91234-5678", "Cba#senha123", false, User.UserStatus.ACTIVE);
        User admin = createUser("Admin", "System", "admin@admin.com", "(11) 99999-9999", "Admin@1234", true, User.UserStatus.ACTIVE);
        User pedro = createUser("Pedro", "Costa", "pedro@email.com", "(11) 99876-5432", "Aaa@pedro123", false, User.UserStatus.INACTIVE);
        User ana = createUser("Ana", "Oliveira", "ana@email.com", "(21) 98765-1234", "Bbb#ana123", true, User.UserStatus.SUSPENDED);

        userRepository.saveAll(Arrays.asList(joao, maria, admin, pedro, ana));

        // Buscar produtos salvos para criar pedidos
        Iterable<Product> allProducts = productRepository.findAll();
        Product[] products = new Product[50];
        int index = 0;
        for (Product p : allProducts) {
            products[index++] = p;
        }

        // Insert de Pedidos - Distribuídos entre usuários com diferentes status

        // João - 5 pedidos (status variados)
        orderRepository.save(createOrder(joao, 3599.88, 3, Order.OrderStatus.DELIVERED, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(30), products[0], products[1], products[4]));
        orderRepository.save(createOrder(joao, 5299.99, 2, Order.OrderStatus.DELIVERED, Order.PaymentMethod.CREDIT_CARD,
                LocalDateTime.now().minusDays(20), products[2], products[5]));
        orderRepository.save(createOrder(joao, 1499.98, 2, Order.OrderStatus.SHIPPED, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(5), products[14], products[23]));
        orderRepository.save(createOrder(joao, 899.00, 1, Order.OrderStatus.PAID, Order.PaymentMethod.DEBIT_CARD,
                LocalDateTime.now().minusDays(3), products[9]));
        orderRepository.save(createOrder(joao, 2999.99, 1, Order.OrderStatus.PENDING, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(1), products[0]));

        // Maria - 8 pedidos (mais ativa)
        orderRepository.save(createOrder(maria, 1089.89, 3, Order.OrderStatus.DELIVERED, Order.PaymentMethod.CREDIT_CARD,
                LocalDateTime.now().minusDays(45), products[15], products[16], products[17]));
        orderRepository.save(createOrder(maria, 899.99, 1, Order.OrderStatus.DELIVERED, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(35), products[3]));
        orderRepository.save(createOrder(maria, 2099.99, 2, Order.OrderStatus.DELIVERED, Order.PaymentMethod.CREDIT_CARD,
                LocalDateTime.now().minusDays(25), products[25], products[26]));
        orderRepository.save(createOrder(maria, 459.90, 1, Order.OrderStatus.SHIPPED, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(10), products[28]));
        orderRepository.save(createOrder(maria, 1599.99, 1, Order.OrderStatus.SHIPPED, Order.PaymentMethod.DEBIT_CARD,
                LocalDateTime.now().minusDays(7), products[29]));
        orderRepository.save(createOrder(maria, 799.98, 2, Order.OrderStatus.CONFIRMED, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(4), products[40], products[41]));
        orderRepository.save(createOrder(maria, 1899.00, 1, Order.OrderStatus.PAID, Order.PaymentMethod.CREDIT_CARD,
                LocalDateTime.now().minusDays(2), products[35]));
        orderRepository.save(createOrder(maria, 599.99, 1, Order.OrderStatus.PENDING, Order.PaymentMethod.PIX,
                LocalDateTime.now(), products[5]));

        // Admin - 3 pedidos
        orderRepository.save(createOrder(admin, 9599.97, 3, Order.OrderStatus.DELIVERED, Order.PaymentMethod.CREDIT_CARD,
                LocalDateTime.now().minusDays(60), products[2], products[7], products[10]));
        orderRepository.save(createOrder(admin, 4899.90, 2, Order.OrderStatus.DELIVERED, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(40), products[8], products[12]));
        orderRepository.save(createOrder(admin, 1299.00, 1, Order.OrderStatus.CONFIRMED, Order.PaymentMethod.DEBIT_CARD,
                LocalDateTime.now().minusDays(5), products[36]));

        // Pedro - 2 pedidos (conta inativa, mas tem histórico)
        orderRepository.save(createOrder(pedro, 2999.98, 2, Order.OrderStatus.DELIVERED, Order.PaymentMethod.CREDIT_CARD,
                LocalDateTime.now().minusDays(90), products[0], products[6]));
        orderRepository.save(createOrder(pedro, 899.90, 1, Order.OrderStatus.CANCELLED, Order.PaymentMethod.BOLETO,
                LocalDateTime.now().minusDays(50), products[37]));

        // Ana - 4 pedidos (conta suspensa)
        orderRepository.save(createOrder(ana, 1799.97, 3, Order.OrderStatus.DELIVERED, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(70), products[20], products[21], products[22]));
        orderRepository.save(createOrder(ana, 599.99, 1, Order.OrderStatus.DELIVERED, Order.PaymentMethod.CREDIT_CARD,
                LocalDateTime.now().minusDays(55), products[44]));
        orderRepository.save(createOrder(ana, 1299.00, 1, Order.OrderStatus.SHIPPED, Order.PaymentMethod.PIX,
                LocalDateTime.now().minusDays(15), products[39]));
        orderRepository.save(createOrder(ana, 299.90, 1, Order.OrderStatus.CANCELLED, Order.PaymentMethod.BOLETO,
                LocalDateTime.now().minusDays(12), products[45]));
    }

    private Product createProduct(String name, Category category, Double price, Product.ProductStatus status, String description) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setStatus(status);
        product.setDescription(description);
        return product;
    }

    private User createUser(String firstName, String lastName, String email, String phone, String password, boolean newsletter, User.UserStatus status) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setNewsletter(newsletter);
        user.setStatus(status);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    private Order createOrder(User user, Double totalValue, Integer totalItems, Order.OrderStatus status,
                              Order.PaymentMethod paymentMethod, LocalDateTime createdAt, Product... products) {
        Order order = new Order(user, totalValue, totalItems);
        order.setStatus(status);
        order.setPaymentMethod(paymentMethod);
        order.setCreatedAt(createdAt);

        // Adicionar items ao pedido
        for (Product product : products) {
            OrderItem item = new OrderItem(product, product.getPrice(), 1, product.getPrice());
            order.addItem(item);
        }

        return order;
    }
}
