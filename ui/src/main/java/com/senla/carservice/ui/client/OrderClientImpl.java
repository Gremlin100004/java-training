package com.senla.carservice.ui.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.DateDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.ui.exception.BusinessException;
import com.senla.carservice.ui.util.ExceptionUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@NoArgsConstructor
@Slf4j
public class OrderClientImpl implements OrderClient {
    private static final String ADD_ORDER_PATH = "orders";
    private static final String GET_ORDERS_PATH = "orders";
    private static final String COMPLETE_ORDER_START_PATH = "orders/";
    private static final String COMPLETE_ORDER_END_PATH = "/complete";
    private static final String CLOSE_ORDER_START_PATH = "orders/";
    private static final String CLOSE_ORDER_END_PATH = "/close";
    private static final String CANCEL_ORDER_START_PATH = "orders/";
    private static final String CANCEL_ORDER_END_PATH = "/cancel";
    private static final String DELETE_ORDER_PATH = "orders/";
    private static final String SHIFT_LEAD_TIME_PATH = "orders/shiftLeadTime";
    private static final String GET_SORT_ORDERS_PATH = "orders?sortParameter=";
    private static final String PARAMETER_START_TIME = "&startPeriod=";
    private static final String PARAMETER_END_TIME = "&endPeriod=";
    private static final String GET_ORDER_MASTERS_START_PATH = "orders/";
    private static final String GET_ORDER_MASTERS_END_PATH = "/masters";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    private static final String ORDER_TRANSFERRED_SUCCESS_MESSAGE = "The order has been transferred to execution status";
    private static final String ADD_ORDER_SUCCESS_MESSAGE = "Order added successfully";
    private static final String ORDER_COMPLETE_SUCCESS_MESSAGE = "The order has been completed successfully";
    private static final String ORDER_CANCEL_SUCCESS_MESSAGE = "The order has been canceled successfully";
    private static final String ORDER_DELETE_SUCCESS_MESSAGE = "The order has been deleted";
    private static final String ORDER_CHANGE_TIME_SUCCESS_MESSAGE = "The order lead time has been changed successfully";
    private static final String GET_NEAREST_FREE_DATE_PATH = "orders/freeDate";
    private static final String EXPORT_ENTITIES_PATH = "orders/csv/export";
    private static final String IMPORT_ENTITIES_PATH = "orders/csv/import";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String addOrder(OrderDto orderDto) {
        log.debug("[addOrder]");
        log.trace("[orderDto: {}]", orderDto);
        try {
            ResponseEntity<OrderDto> response = restTemplate.postForEntity(
                ADD_ORDER_PATH, orderDto, OrderDto.class);
            OrderDto receivedOrderDto = response.getBody();
            if (receivedOrderDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return ADD_ORDER_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public List<OrderDto> getOrders() {
        log.debug("[getOrders]");
        try {
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(GET_ORDERS_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                throw new BusinessException(WARNING_SERVER_MESSAGE);
            }
            return Arrays.asList(arrayOrdersDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String completeOrder(Long idOrder) {
        log.debug("[completeOrder]");
        log.trace("[idOrder: {}]", idOrder);
        try {
            restTemplate.put(COMPLETE_ORDER_START_PATH + idOrder + COMPLETE_ORDER_END_PATH, OrderDto.class);
            return ORDER_TRANSFERRED_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String closeOrder(Long idOrder) {
        log.debug("[closeOrder]");
        log.trace("[idOrder: {}]", idOrder);
        try {
            restTemplate.put(CLOSE_ORDER_START_PATH + idOrder + CLOSE_ORDER_END_PATH, OrderDto.class);
            return ORDER_COMPLETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String cancelOrder(Long idOrder) {
        log.debug("[cancelOrder]");
        log.trace("[idOrder: {}]", idOrder);
        try {
            restTemplate.put(CANCEL_ORDER_START_PATH + idOrder + CANCEL_ORDER_END_PATH, OrderDto.class);
            return ORDER_CANCEL_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String deleteOrder(Long idOrder) {
        log.debug("[deleteOrder]");
        log.trace("[idOrder: {}]", idOrder);
        try {
            restTemplate.delete(DELETE_ORDER_PATH + idOrder, OrderDto.class);
            return ORDER_DELETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String shiftLeadTime(OrderDto orderDto) {
        log.debug("[shiftLeadTime]");
        log.trace("[orderDto: {}]", orderDto);
        try {
            restTemplate.put(SHIFT_LEAD_TIME_PATH, orderDto, OrderDto.class);
            return ORDER_CHANGE_TIME_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public List<OrderDto> getSortOrders(String sortParameter) {
        log.debug("[getOrdersSortByFilingDate]");
        log.trace("[sortParameter: {}]", sortParameter);
        try {
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(
                GET_SORT_ORDERS_PATH + sortParameter, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                throw new BusinessException(WARNING_SERVER_MESSAGE);
            }
            return Arrays.asList(arrayOrdersDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public List<OrderDto> getSortOrdersByPeriod(String sortParameter, String startPeriod, String endPeriod) {
        log.debug("[getOrdersSortByFilingDate]");
        log.trace("[sortParameter: {}]", sortParameter);
        try {
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(
                GET_SORT_ORDERS_PATH + sortParameter + PARAMETER_START_TIME + startPeriod +
                PARAMETER_END_TIME + endPeriod, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                throw new BusinessException(WARNING_SERVER_MESSAGE);
            }
            return Arrays.asList(arrayOrdersDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public List<MasterDto> getOrderMasters(Long orderId) {
        log.debug("[getOrderMasters]");
        log.trace("[orderId: {}]", orderId);
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                GET_ORDER_MASTERS_START_PATH + orderId + GET_ORDER_MASTERS_END_PATH, MasterDto[].class);
            MasterDto[] arrayMasterDto = response.getBody();
            if (arrayMasterDto == null) {
                throw new BusinessException(WARNING_SERVER_MESSAGE);
            }
            return Arrays.asList(arrayMasterDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public DateDto getNearestFreeDate() {
        log.debug("[getNearestFreeDate]");
        try {
            ResponseEntity<DateDto> response = restTemplate.getForEntity(
                GET_NEAREST_FREE_DATE_PATH, DateDto.class);
            DateDto dateDto = response.getBody();
            if (dateDto == null) {
                throw new BusinessException(WARNING_SERVER_MESSAGE);
            }
            return dateDto;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String exportEntities() {
        log.debug("[exportEntities]");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                EXPORT_ENTITIES_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String importEntities() {
        log.debug("[importEntities]");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                IMPORT_ENTITIES_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

}
