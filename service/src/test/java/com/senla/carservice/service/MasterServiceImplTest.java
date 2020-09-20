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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class MasterServiceImplTest {

    private static final Long RIGHT_NUMBER_MASTERS = 2L;
    private static final Long ID_MASTER = 1L;
    private static final Long ID_OTHER_MASTER = 2L;
    private static final String PARAMETER_NAME = "test name";
    @Autowired
    private MasterService masterService;
    @Autowired
    private MasterDao masterDao;

    @Test
    void MasterServiceImpl_getMasters() {
        List<Master> masters = getTestMasters();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        
        List<Master> resultMasters = masterService.getMasters();
        Assertions.assertNotNull(resultMasters);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMasters.size());
        Assertions.assertFalse(resultMasters.isEmpty());
        Assertions.assertEquals(masters, resultMasters);
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasters_masterDao_getAllRecords_daoException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getAllRecords();

        Assertions.assertThrows(DaoException.class, () -> masterService.getMasters());
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_addMaster() {
        Assertions.assertDoesNotThrow(() -> masterService.addMaster(ArgumentMatchers.anyString()));
        Mockito.verify(masterDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Master.class));
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getFreeMastersByDate() {
        Date date = new Date();
        List<Master> masters = getTestMasters();
        Mockito.doReturn(masters).when(masterDao).getFreeMasters(date);

        List<Master> resultMasters = masterService.getFreeMastersByDate(date);
        Assertions.assertNotNull(resultMasters);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMasters.size());
        Assertions.assertFalse(resultMasters.isEmpty());
        Assertions.assertEquals(masters, resultMasters);
        Mockito.verify(masterDao, Mockito.times(1)).getFreeMasters(date);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getFreeMastersByDate_masterDao_getFreeMasters_daoException() {
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
    void MasterServiceImpl_deleteMaster_masterDao_findById_daoException() {
        Master master = getTestMaster();
        Mockito.doThrow(DaoException.class).when(masterDao).findById(ID_MASTER);

        Assertions.assertThrows(DaoException.class, () -> masterService.deleteMaster(ID_MASTER));
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(masterDao, Mockito.never()).updateRecord(master);
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterByAlphabet() {
        List<Master> masters = getTestMasters();
        Mockito.doReturn(masters).when(masterDao).getMasterSortByAlphabet();

        List<Master> resultMasters = masterService.getMasterByAlphabet();
        Assertions.assertNotNull(resultMasters);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMasters.size());
        Assertions.assertFalse(resultMasters.isEmpty());
        Assertions.assertEquals(masters, resultMasters);
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByAlphabet();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterByAlphabet_masterDao_getMasterSortByAlphabet_daoException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterSortByAlphabet();

        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterByAlphabet());
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByAlphabet();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterByBusy() {
        List<Master> masters = getTestMasters();
        Mockito.doReturn(masters).when(masterDao).getMasterSortByBusy();

        List<Master> resultMasters = masterService.getMasterByBusy();
        Assertions.assertNotNull(resultMasters);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMasters.size());
        Assertions.assertFalse(resultMasters.isEmpty());
        Assertions.assertEquals(masters, resultMasters);
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByBusy();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getMasterByBusy_masterDao_getMasterSortByBusy_daoException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterSortByBusy();

        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterByBusy());
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByBusy();
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_getNumberMasters() {
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();

        Long resultNumberMasters = masterService.getNumberMasters();
        Assertions.assertNotNull(resultNumberMasters);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultNumberMasters);
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
    }

    private Master getTestMaster() {
        Master master = new Master(PARAMETER_NAME);
        master.setId(ID_MASTER);
        return master;
    }

    private List<Master> getTestMasters() {
        Master masterOne = getTestMaster();
        Master masterTwo = getTestMaster();
        masterTwo.setId(ID_OTHER_MASTER);
        return Arrays.asList(masterOne, masterTwo);
    }
}