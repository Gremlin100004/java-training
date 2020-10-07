package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.config.TestConfig;
import com.senla.carservice.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class MasterServiceImplTest {

    private static final Long RIGHT_NUMBER_MASTERS = 2L;
    private static final Long WRONG_NUMBER_MASTERS = 0L;
    private static final Long ID_MASTER = 1L;
    private static final Long ID_OTHER_MASTER = 2L;
    private static final String PARAMETER_NAME = "test name";
    private static final Long ID_ORDER = 1L;
    private static final Long ID_ORDER_OTHER = 2L;
    private static final Long RIGHT_NUMBER_ORDERS = 2L;
    private static final String PARAMETER_AUTOMAKER = "test automaker";
    private static final String PARAMETER_MODEL = "test model";
    private static final String PARAMETER_REGISTRATION_NUMBER = "registrationNumber";
    @Autowired
    private MasterService masterService;
    @Autowired
    private MasterDao masterDao;

    @Test
    void MasterServiceImpl_getMasters() {
        List<Master> masters = getTestMasters();
        List<MasterDto> mastersDto = getTestMastersDto();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();

        List<MasterDto> resultMastersDto = masterService.getMasters();
        Assertions.assertNotNull(resultMastersDto);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMastersDto.size());
        Assertions.assertFalse(resultMastersDto.isEmpty());
        Assertions.assertEquals(mastersDto, resultMastersDto);
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasters_masterDao_getAllRecords_emptyRecords() {
        Mockito.doThrow(DaoException.class).when(masterDao).getAllRecords();

        Assertions.assertThrows(DaoException.class, () -> masterService.getMasters());
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_addMaster() {
        Master master = getTestMaster();
        MasterDto masterDto = getTestMasterDto();
        Mockito.doReturn(master).when(masterDao).saveRecord(ArgumentMatchers.any(Master.class));

        Assertions.assertDoesNotThrow(() -> masterService.addMaster(masterDto));
        Mockito.verify(masterDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Master.class));
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getFreeMastersByDate() {
        Date date = new Date();
        List<Master> masters = getTestMasters();
        List<MasterDto> mastersDto = getTestMastersDto();
        Mockito.doReturn(masters).when(masterDao).getFreeMasters(date);

        List<MasterDto> resultMastersDto = masterService.getFreeMastersByDate(date);
        Assertions.assertNotNull(resultMastersDto);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMastersDto.size());
        Assertions.assertFalse(resultMastersDto.isEmpty());
        Assertions.assertEquals(mastersDto, resultMastersDto);
        Mockito.verify(masterDao, Mockito.times(1)).getFreeMasters(date);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getFreeMastersByDate_masterDao_getFreeMasters_emptyList() {
        Date date = new Date();
        Mockito.doThrow(DaoException.class).when(masterDao).getFreeMasters(date);

        Assertions.assertThrows(DaoException.class, () -> masterService.getFreeMastersByDate(date));
        Mockito.verify(masterDao, Mockito.times(1)).getFreeMasters(date);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getNumberFreeMastersByDate() {
        Date date = new Date();
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(date);

        Long resultNumberFreeMasters = masterService.getNumberFreeMastersByDate(date);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultNumberFreeMasters);
        Assertions.assertNotNull(resultNumberFreeMasters);
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(date);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_deleteMaster() {
        Master master = getTestMaster();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);

        Assertions.assertDoesNotThrow(() -> masterService.deleteMaster(ID_MASTER));
        Assertions.assertTrue(master.getDeleteStatus());
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(masterDao, Mockito.times(1)).updateRecord(master);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_deleteMaster_masterDao_findById_wrongId() {
        Master master = getTestMaster();
        Mockito.doThrow(DaoException.class).when(masterDao).findById(ID_MASTER);

        Assertions.assertThrows(DaoException.class, () -> masterService.deleteMaster(ID_MASTER));
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(masterDao, Mockito.never()).updateRecord(master);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_deleteMaster_masterDeleted() {
        Master master = getTestMaster();
        master.setDeleteStatus(true);
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);

        Assertions.assertThrows(BusinessException.class, () -> masterService.deleteMaster(ID_MASTER));
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(masterDao, Mockito.never()).updateRecord(master);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterByAlphabet() {
        List<Master> masters = getTestMasters();
        List<MasterDto> mastersDto = getTestMastersDto();
        Mockito.doReturn(masters).when(masterDao).getMasterSortByAlphabet();

        List<MasterDto> resultMastersDto = masterService.getMasterByAlphabet();
        Assertions.assertNotNull(resultMastersDto);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMastersDto.size());
        Assertions.assertFalse(resultMastersDto.isEmpty());
        Assertions.assertEquals(mastersDto, resultMastersDto);
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByAlphabet();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterByAlphabet_masterDao_getMasterSortByAlphabet_emptyList() {
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterSortByAlphabet();

        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterByAlphabet());
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByAlphabet();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterByBusy() {
        List<Master> masters = getTestMasters();
        List<MasterDto> mastersDto = getTestMastersDto();
        Mockito.doReturn(masters).when(masterDao).getMasterSortByBusy();

        List<MasterDto> resultMastersDto = masterService.getMasterByBusy();
        Assertions.assertNotNull(resultMastersDto);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMastersDto.size());
        Assertions.assertFalse(resultMastersDto.isEmpty());
        Assertions.assertEquals(mastersDto, resultMastersDto);
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByBusy();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterByBusy_masterDao_getMasterSortByBusy_emptyList() {
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterSortByBusy();

        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterByBusy());
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByBusy();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getNumberMasters() {
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();

        Assertions.assertDoesNotThrow(() -> masterService.checkMasters());
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getNumberMasters_masterDao_getNumberMasters_zeroNumberMasters_getNumberMasters_masterDao_getNumberMasters_zeroNumberMasters() {
        Mockito.doReturn(WRONG_NUMBER_MASTERS).when(masterDao).getNumberMasters();

        Assertions.assertThrows(BusinessException.class, () -> masterService.checkMasters());
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterOrders() {
        Master master = getTestMaster();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        List<Order> orders = getTestOrders();
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        Mockito.doReturn(orders).when(masterDao).getMasterOrders(master);

        List<OrderDto> resultOrdersDto = masterService.getMasterOrders(ID_MASTER);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(masterDao, Mockito.times(1)).getMasterOrders(master);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterOrders_masterDao_findById_wrongId() {
        Master master = getTestMaster();
        Mockito.doThrow(DaoException.class).when(masterDao).findById(ID_MASTER);

        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterOrders(ID_MASTER));
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(masterDao, Mockito.never()).getMasterOrders(master);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterOrders_masterDao_getMasterOrders_emptyMasterListOrders() {
        Master master = getTestMaster();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterOrders(master);

        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterOrders(ID_MASTER));
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(masterDao, Mockito.times(1)).getMasterOrders(master);
        Mockito.reset(masterDao);
    }

    private Master getTestMaster() {
        Master master = new Master();
        master.setName(PARAMETER_NAME);
        master.setId(ID_MASTER);
        return master;
    }

    private MasterDto getTestMasterDto() {
        MasterDto masterDto = new MasterDto();
        masterDto.setName(PARAMETER_NAME);
        masterDto.setId(ID_MASTER);
        return masterDto;
    }

    private List<Master> getTestMasters() {
        Master masterOne = getTestMaster();
        Master masterTwo = getTestMaster();
        masterTwo.setId(ID_OTHER_MASTER);
        return Arrays.asList(masterOne, masterTwo);
    }

    private List<MasterDto> getTestMastersDto() {
        MasterDto masterDtoOne = getTestMasterDto();
        MasterDto masterDtoTwo = getTestMasterDto();
        masterDtoTwo.setId(ID_OTHER_MASTER);
        return Arrays.asList(masterDtoOne, masterDtoTwo);
    }

    private List<OrderDto> getTestOrdersDto() {
        OrderDto orderDtoOne = getTestOrderDto();
        OrderDto orderDtoTwo = getTestOrderDto();
        orderDtoTwo.setId(ID_ORDER_OTHER);
        return Arrays.asList(orderDtoOne, orderDtoTwo);
    }

    private OrderDto getTestOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(ID_ORDER);
        orderDto.setAutomaker(PARAMETER_AUTOMAKER);
        orderDto.setModel(PARAMETER_MODEL);
        orderDto.setRegistrationNumber(PARAMETER_REGISTRATION_NUMBER);
        return orderDto;
    }

    private List<Order> getTestOrders() {
        Order orderOne = getTestOrder();
        Order orderTwo = getTestOrder();
        orderTwo.setId(ID_ORDER_OTHER);
        return Arrays.asList(orderOne, orderTwo);
    }

    private Order getTestOrder() {
        Order order = new Order();
        order.setAutomaker(PARAMETER_AUTOMAKER);
        order.setModel(PARAMETER_MODEL);
        order.setRegistrationNumber(PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        order.setCreationTime(new Date());
        order.setExecutionStartTime(new Date());
        order.setLeadTime(new Date());
        return order;
    }
}