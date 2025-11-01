package com.ecommerce.service;

import com.ecommerce.api.dto.OrderCreateRequestDTO;
import com.ecommerce.api.dto.OrderItemRequestDTO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repositoty.OrderRepository;
import com.ecommerce.repositoty.ProductRepository;
import com.ecommerce.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Iterable<Order> findAllByStatus(Order.OrderStatus status) {
        System.out.println("Finding orders by status: " + status);
        return orderRepository.findAllByStatus(status);
    }

    public Iterable<Order> findAll() {
        System.out.println("Finding all orders");
        return orderRepository.findAll();
    }

    public Iterable<Order> findAllByStatusAndUserId(Order.OrderStatus status, Long id) {
        System.out.println("Finding orders by status: " + status + " and user ID: " + id);
        return orderRepository.findAllByStatusAndUserId(status, id);
    }

    public Iterable<Order> findAllByUserId(Long id) {
        System.out.println("Finding orders by user ID: " + id);
        return orderRepository.findAllByUserId(id);
    }

    public Order save(Order order) {
        System.out.println("Saving order: " + order);
        return orderRepository.save(order);
    }

    public long count() {
        return orderRepository.count();
    }

    public double totalSales() {
        System.out.println("Calculating total sales");
        Iterable<Order> allOrders = findAll();
        return calculateTotalSales(allOrders);
    }

    /**
     * Calcula o total de vendas somando o valor de todos os pedidos
     */
    private double calculateTotalSales(Iterable<Order> orders) {
        /**
         * outra alternativa usando a api de streams do java 8:
         * return StreamSupport.stream(orders.spliterator(), false)
         *     .mapToDouble(Order::getTotalValue)
         *     .sum();
         */
        double total = 0.0;
        for (Order order : orders) {
            total += order.getTotalValue();
        }
        return total;
    }

    public List<Order> recentOrders(Integer limit) {
        System.out.println("Fetching recent orders with limit: " + limit);

        return getRecentOrders(orderRepository.findAll(), 5);
    }

    /**
     * Calcula os produtos mais vendidos baseado na quantidade total vendida
     */
    private List<Map.Entry<Product, Integer>> getTopProducts(Iterable<Order> orders, int limit) {
        // Mapa para contar quantas vezes cada produto foi vendido
        /**
         * outra alternativa usando a api de streams do java 8:
         * Map<Product, Integer> productSalesCount = StreamSupport
         *     .stream(orders.spliterator(), false)
         *     .flatMap(order -> order.getItems().stream())
         *     .collect(Collectors.groupingBy(
         *         OrderItem::getProduct,
         *         Collectors.summingInt(OrderItem::getQuantity)
         *     ));
         */
        Map<Product, Integer> productSalesCount = new HashMap<>();

        // Percorrer todos os pedidos
        for (Order order : orders) {
            // Percorrer todos os itens do pedido
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();

                // Se o produto já existe no mapa, soma a quantidade
                if (productSalesCount.containsKey(product)) {
                    int currentCount = productSalesCount.get(product);
                    productSalesCount.put(product, currentCount + quantity);
                } else {
                    // Se não existe, adiciona com a quantidade inicial
                    productSalesCount.put(product, quantity);
                }
            }
        }

        // Converter o Map para uma List de entradas para poder ordenar
        /**
         * outra alternativa usando a api de streams do java 8:
         * return productSalesCount.entrySet().stream()
         *     .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
         *     .limit(limit)
         *     .collect(Collectors.toList());
         */
        List<Map.Entry<Product, Integer>> productList = new ArrayList<>(productSalesCount.entrySet());

        // Ordenar por quantidade vendida (decrescente)
        productList.sort(new Comparator<Map.Entry<Product, Integer>>() {
            @Override
            public int compare(Map.Entry<Product, Integer> e1, Map.Entry<Product, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        // Retornar apenas os primeiros 'limit' produtos
        if (productList.size() > limit) {
            return productList.subList(0, limit);
        }
        return productList;
    }

    /**
     * Retorna os pedidos mais recentes ordenados por data
     */
    private List<Order> getRecentOrders(Iterable<Order> orders, int limit) {
        // Converter Iterable para List
        /**
         * uma alternativa usando a api de streams do java 8:
         * List<Order> orderList = StreamSupport
         *     .stream(orders.spliterator(), false)
         *     .collect(Collectors.toList());
         */
        List<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            orderList.add(order);
        }

        // Ordenar por data decrescente (mais recente primeiro)
        /**
         * outra alternativa usando a api de streams do java 8:
         * orderList = orderList.stream()
         *     .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
         *     .collect(Collectors.toList());
         */
        orderList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });

        // Retornar apenas os primeiros 'limit' pedidos
        /**
         * outra alternativa usando a api de streams do java 8:
         * return orderList.stream()
         *    .limit(limit)
         *    .collect(Collectors.toList());
         */
        if (orderList.size() > limit) {
            return orderList.subList(0, limit);
        }
        return orderList;
    }

    public List<Map.Entry<Product, Integer>> topProducts(int limit) {
        System.out.println("Fetching top products with limit: " + limit);

        Iterable<Order> allOrders = orderRepository.findAll();

        return getTopProducts(allOrders, 5);
    }

    public Order createOrderFromApi(OrderCreateRequestDTO request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.userId()));

        Order order = new Order(user);

        double totalValue = 0.0;
        int totalItems = 0;

        for (OrderItemRequestDTO itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException(
                            "Product not found with id: " + itemRequest.productId()));

            double unitPrice = product.getPrice();
            double subtotal = unitPrice * itemRequest.quantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setSubtotal(subtotal);

            order.addItem(orderItem);

            totalValue += subtotal;
            totalItems += itemRequest.quantity();
        }

        order.setTotalValue(totalValue);
        order.setTotalItems(totalItems);

        return orderRepository.save(order);
    }
}
