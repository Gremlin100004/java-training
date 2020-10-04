package com.senla.carservice.ui.service;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.ui.exception.BusinessException;
import com.senla.carservice.ui.util.ExceptionUtil;
import com.senla.carservice.ui.util.StringMaster;
import com.senla.carservice.ui.util.StringOrder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
@NoArgsConstructor
@Slf4j
public class OrderService {
    private static final String ADD_ORDER_PATH = "orders";
    private static final String CHECK_ORDER_DEADLINES_PATH = "orders/check-dates";
    private static final String CHECK_ORDERS_PATH = "orders/check";
    private static final String GET_ORDERS_PATH = "orders";
    private static final String COMPLETE_ORDER_START_PATH = "orders/";
    private static final String COMPLETE_ORDER_END_PATH = "/complete";
    private static final String CLOSE_ORDER_START_PATH = "orders/";
    private static final String CLOSE_ORDER_END_PATH = "/close";
    private static final String CANCEL_ORDER_START_PATH = "orders/";
    private static final String CANCEL_ORDER_END_PATH = "/cancel";
    private static final String DELETE_ORDER_PATH = "orders/";
    private static final String SHIFT_LEAD_TIME_PATH = "orders/shift-lead-time";
    private static final String GET_ORDERS_SORT_BY_FILING_DATE_PATH = "orders/sort-by-filing-date";
    private static final String GET_ORDERS_SORT_BY_EXECUTION_DATE_PATH = "orders/sort-by-execution-date";
    private static final String GET_ORDERS_SORT_BY_PLANNED_START_DATE_PATH = "orders/sort-by-planned-start-date";
    private static final String GET_ORDERS_SORT_BY_PRICE_PATH = "orders/sort-by-price";
    private static final String GET_EXECUTE_ORDER_FILING_DATE_PATH = "orders/execute/sort-by-filing-date";
    private static final String GET_EXECUTE_ORDER_EXECUTION_DATE_PATH = "orders/execute/sort-by-execution-date";
    private static final String GET_COMPLETED_ORDERS_FILING_DATE_PATH = "orders/complete/sort-by-filing-date";
    private static final String GET_COMPLETED_ORDERS_EXECUTION_DATE_PATH = "orders/complete/sort-by-execution-date";
    private static final String GET_COMPLETED_ORDERS_PRICE_PATH = "orders/complete/sort-by-price";
    private static final String GET_CANCELED_ORDERS_FILING_DATE_PATH = "orders/canceled/sort-by-filing-date";
    private static final String GET_CANCELED_ORDERS_EXECUTION_DATE_PATH = "orders/canceled/sort-by-execution-date";
    private static final String GET_CANCELED_ORDERS_PRICE_PATH = "orders/canceled/sort-by-price";
    private static final String GET_DELETED_ORDERS_FILING_DATE_PATH = "orders/deleted/sort-by-filing-date";
    private static final String GET_DELETED_ORDERS_EXECUTION_DATE_PATH = "orders/deleted/sort-by-execution-date";
    private static final String GET_DELETED_ORDERS_PRICE = "orders/deleted/sort-by-price";
    private static final String GET_MASTER_ORDERS_PATH = "orders/master-orders";
    private static final String GET_ORDER_MASTERS_PATH = "orders/masters";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${carservice.connection.url:http://localhost:8080/}")
    private String connectionUrl;

    public String addOrder(OrderDto orderDto) {
        log.debug("Method addOrder");
        log.trace("Parameter orderDto: {}", orderDto);
        try {
            ResponseEntity<OrderDto> response = restTemplate.postForEntity(
                connectionUrl + ADD_ORDER_PATH, orderDto, OrderDto.class);
            OrderDto receivedOrderDto = response.getBody();
            if (receivedOrderDto == null) {
                return "There are no message from server";
            }
            return "Order added successfully";
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String checkOrderDeadlines(String stringExecutionStartTime, String stringLeadTime) {
        log.debug("Method addOrder");
        log.trace("Parameter stringExecutionStartTime: {}, stringLeadTime: {}", stringExecutionStartTime, stringLeadTime);
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(connectionUrl + CHECK_ORDER_DEADLINES_PATH)
                .queryParam("stringExecutionStartTime", stringExecutionStartTime)
                .queryParam("stringLeadTime", stringLeadTime);
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                builder.toUriString(), ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return "verification was successfully";
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String checkOrders() {
        log.debug("Method checkOrders");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                connectionUrl + CHECK_ORDERS_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public List<OrderDto> getOrders() {
        log.debug("Method getOrders");
        try {
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(
                connectionUrl + GET_ORDERS_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                throw new BusinessException("Error, there are no masters");
            }
            return Arrays.asList(arrayOrdersDto);
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessageFromException(exception));
        }
    }

    public String completeOrder(Long idOrder) {
        log.debug("Method completeOrder");
        log.trace("Parameter idOrder: {}", idOrder);
        try {
            restTemplate.put(
                connectionUrl + COMPLETE_ORDER_START_PATH + idOrder + COMPLETE_ORDER_END_PATH, OrderDto.class);
            return "The order has been transferred to execution status";
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String closeOrder(Long idOrder) {
        log.debug("Method closeOrder");
        log.trace("Parameter idOrder: {}", idOrder);
        try {
            restTemplate.put(
                connectionUrl + CLOSE_ORDER_START_PATH + idOrder + CLOSE_ORDER_END_PATH, OrderDto.class);
            return "The order has been completed successfully";
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String cancelOrder(Long idOrder) {
        log.debug("Method cancelOrder");
        log.trace("Parameter idOrder: {}", idOrder);
        try {
            restTemplate.put(connectionUrl + CANCEL_ORDER_START_PATH + idOrder + CANCEL_ORDER_END_PATH, OrderDto.class);
            return "The order has been canceled";
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String deleteOrder(Long idOrder) {
        log.debug("Method deleteOrder");
        log.trace("Parameter idOrder: {}", idOrder);
        try {
            restTemplate.delete(connectionUrl + DELETE_ORDER_PATH + idOrder, OrderDto.class);
            return "The order has been deleted";
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String shiftLeadTime(OrderDto orderDto) {
        log.debug("Method shiftLeadTime");
        log.trace("Parameter orderDto: {}", orderDto);
        try {
            restTemplate.put(connectionUrl + SHIFT_LEAD_TIME_PATH, orderDto, OrderDto.class);
            return "The order lead time has been changed successfully";
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getOrdersSortByFilingDate() {
        log.debug("Method getOrdersSortByFilingDate");
        try {
            ResponseEntity<OrderDto[]> response =
                restTemplate.getForEntity(connectionUrl + GET_ORDERS_SORT_BY_FILING_DATE_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getOrdersSortByExecutionDate() {
        log.debug("Method getOrdersSortByExecutionDate");
        try {
            ResponseEntity<OrderDto[]> response =
                restTemplate.getForEntity(connectionUrl + GET_ORDERS_SORT_BY_EXECUTION_DATE_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getOrdersSortByPlannedStartDate() {
        log.debug("Method getOrdersSortByPlannedStartDate");
        try {
            ResponseEntity<OrderDto[]> response =
                restTemplate.getForEntity(connectionUrl + GET_ORDERS_SORT_BY_PLANNED_START_DATE_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getOrdersSortByPrice() {
        log.debug("Method getOrdersSortByPrice");
        try {
            ResponseEntity<OrderDto[]> response =
                restTemplate.getForEntity(connectionUrl + GET_ORDERS_SORT_BY_PRICE_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getExecuteOrderFilingDate() {
        log.debug("Method getExecuteOrderFilingDate");
        try {
            ResponseEntity<OrderDto[]> response =
                restTemplate.getForEntity(connectionUrl + GET_EXECUTE_ORDER_FILING_DATE_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getExecuteOrderExecutionDate() {
        log.debug("Method getExecuteOrderExecutionDate");
        try {
            ResponseEntity<OrderDto[]> response =
                restTemplate.getForEntity(connectionUrl + GET_EXECUTE_ORDER_EXECUTION_DATE_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getCompletedOrdersFilingDate(String startPeriod, String endPeriod) {
        log.debug("Method getCompletedOrdersFilingDate");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_COMPLETED_ORDERS_FILING_DATE_PATH)
                    .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getCompletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        log.debug("Method getCompletedOrdersExecutionDate");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_COMPLETED_ORDERS_EXECUTION_DATE_PATH)
                    .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getCompletedOrdersPrice(String startPeriod, String endPeriod) {
        log.debug("Method getCompletedOrdersPrice");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_COMPLETED_ORDERS_PRICE_PATH)
                    .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getCanceledOrdersFilingDate(String startPeriod, String endPeriod) {
        log.debug("Method getCanceledOrdersFilingDate");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_CANCELED_ORDERS_FILING_DATE_PATH)
                    .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getCanceledOrdersExecutionDate(String startPeriod, String endPeriod) {
        log.debug("Method getCanceledOrdersExecutionDate");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_CANCELED_ORDERS_EXECUTION_DATE_PATH)
                    .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getCanceledOrdersPrice(String startPeriod, String endPeriod) {
        log.debug("Method getCanceledOrdersPrice");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_CANCELED_ORDERS_PRICE_PATH)
                    .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getDeletedOrdersFilingDate(String startPeriod, String endPeriod) {
        log.debug("Method getDeletedOrdersFilingDate");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_DELETED_ORDERS_FILING_DATE_PATH)
                    .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getDeletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        log.debug("Method getDeletedOrdersExecutionDate");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_DELETED_ORDERS_EXECUTION_DATE_PATH)
                    .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getDeletedOrdersPrice(String startPeriod, String endPeriod) {
        log.debug("Method getDeletedOrdersPrice");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_DELETED_ORDERS_PRICE)
                .queryParam("startPeriod", startPeriod).queryParam("endPeriod", endPeriod);
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(builder.toUriString(), OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            return StringOrder.getStringFromOrder(Arrays.asList(arrayOrdersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getMasterOrders(MasterDto masterDto) {
        log.debug("Method getMasterOrders");
        log.trace("Parameter masterDto: {}", masterDto);
        try {
            ResponseEntity<OrderDto[]> response =
                restTemplate.postForEntity(connectionUrl + GET_MASTER_ORDERS_PATH, masterDto, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return "There are no message from server";
            }
            List<OrderDto> ordersDto = Arrays.asList(arrayOrdersDto);
            return StringOrder.getStringFromOrder(ordersDto);
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getOrderMasters(OrderDto orderDto) {
        log.debug("Method getOrderMasters");
        log.trace("Parameter orderDto: {}", orderDto);
        try {
            ResponseEntity<MasterDto[]> response =
                restTemplate.postForEntity(connectionUrl + GET_ORDER_MASTERS_PATH, orderDto, MasterDto[].class);
            MasterDto[] arrayMasterDto = response.getBody();
            if (arrayMasterDto == null) {
                return "There are no message from server";
            }
            List<MasterDto> mastersDto = Arrays.asList(arrayMasterDto);
            return StringMaster.getStringFromMasters(mastersDto);
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }
}