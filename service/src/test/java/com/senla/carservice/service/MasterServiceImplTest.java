package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.dto.MasterDto;
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
        MasterDto masterDto = getTestMasterDto();

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
        MasterDto masterDto = getTestMasterDto();

        Assertions.assertDoesNotThrow(() -> masterService.deleteMaster(masterDto));
        Mockito.verify(masterDao, Mockito.times(1)).updateRecord(ArgumentMatchers.any(Master.class));
        Mockito.reset(masterDao);
    }

    @Test
    void MasterServiceImpl_deleteMaster_masterDeleted() {
        Master master = getTestMaster();
        MasterDto masterDto = getTestMasterDto();
        masterDto.setDeleteStatus(true);

        Assertions.assertThrows(BusinessException.class, () -> masterService.deleteMaster(masterDto));
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
}