package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.service.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class MasterServiceImplTest {

    private static final Long RIGHT_NUMBER_MASTERS = 2L;
    private static final Long ID_MASTER = 1L;
    private static final String PARAMETER_NAME = "test name";
    @Autowired
    private MasterService masterService;
    @Autowired
    private MasterDao masterDao;

    @Test
    void checkGetMastersShouldReturnList() {
        List<Master> masters = getTestMasters();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        List<Master> resultMasters = masterService.getMasters();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getAllRecords();
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetMastersShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getAllRecords();
        Assertions.assertThrows(DaoException.class, () -> masterService.getMasters());
    }

    @Test
    void checkAddMasterShouldCreateMaster() {
        masterService.addMaster(ArgumentMatchers.anyString());
        Mockito.verify(masterDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Master.class));
    }

    @Test
    void checkGetFreeMastersByDateShouldReturnList() {
        Date date = new Date();
        List<Master> masters = getTestMasters();
        Mockito.doReturn(masters).when(masterDao).getFreeMasters(date);
        List<Master> resultMasters = masterService.getFreeMastersByDate(date);
        Mockito.verify(masterDao, Mockito.times(1)).getFreeMasters(date);
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetFreeMastersByDateShouldThrowException() {
        Date date = new Date();
        Mockito.doThrow(DaoException.class).when(masterDao).getFreeMasters(date);
        Assertions.assertThrows(DaoException.class, () -> masterService.getFreeMastersByDate(date));
    }

    @Test
    void checkGetNumberFreeMastersByDateShouldReturnLong() {
        Date date = new Date();
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(date);
        Long resultNumberFreeMasters = masterService.getNumberFreeMastersByDate(date);
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(date);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultNumberFreeMasters);
    }

    @Test
    void checkDeleteMasterShouldDeleteMasterById() {
        Master master = getTestMaster();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        masterService.deleteMaster(ID_MASTER);
        Mockito.verify(masterDao, Mockito.atLeastOnce()).findById(ID_MASTER);
        Mockito.verify(masterDao, Mockito.times(1)).updateRecord(master);
        Assertions.assertTrue(master.getDeleteStatus());
    }

    @Test
    void checkDeleteMasterShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(masterDao).findById(ID_MASTER);
        Assertions.assertThrows(DaoException.class, () -> masterService.deleteMaster(ID_MASTER));
    }

    @Test
    void checkGetMasterByAlphabetShouldReturnList() {
        List<Master> masters = getTestMasters();
        Mockito.doReturn(masters).when(masterDao).getMasterSortByAlphabet();
        List<Master> resultMasters = masterService.getMasterByAlphabet();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getMasterSortByAlphabet();
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetMasterByAlphabetShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterSortByAlphabet();
        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterByAlphabet());
    }

    @Test
    void checkGetMasterByBusyShouldReturnList() {
        List<Master> masters = getTestMasters();
        Mockito.doReturn(masters).when(masterDao).getMasterSortByBusy();
        List<Master> resultMasters = masterService.getMasterByBusy();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getMasterSortByBusy();
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetMasterByBusyShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterSortByBusy();
        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterByBusy());
    }

    @Test
    void checkGetNumberMastersShouldReturnNumber() {
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Long resultNumberMasters = masterService.getNumberMasters();
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultNumberMasters);
    }

    private Master getTestMaster() {
        Master master = new Master(PARAMETER_NAME);
        master.setId(ID_MASTER);
        return master;
    }

    private List<Master> getTestMasters() {
        return Collections.singletonList(getTestMaster());
    }
}