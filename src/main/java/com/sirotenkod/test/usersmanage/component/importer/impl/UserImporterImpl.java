package com.sirotenkod.test.usersmanage.component.importer.impl;

import com.sirotenkod.test.usersmanage.component.importer.exception.FailedImportException;
import com.sirotenkod.test.usersmanage.dao.UserDAO;
import com.sirotenkod.test.usersmanage.dto.UserDTO;
import com.sirotenkod.test.usersmanage.component.importer.UserImporter;
import com.sirotenkod.test.usersmanage.service.UserService;
import com.sirotenkod.test.usersmanage.utils.sheet.BeanReader;
import com.sirotenkod.test.usersmanage.utils.sheet.SheetReader;
import com.sirotenkod.test.usersmanage.utils.sheet.exception.BeanReaderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserImporterImpl implements UserImporter {
    private final UserService userService;

    @Autowired
    public UserImporterImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserDAO> importFromSheet(InputStream inputStream) {
        SheetReader sheetReader = new SheetReader(inputStream);

        Integer numberOfSheets = sheetReader.getNumberOfSheets();

        List<UserDAO> users = new ArrayList<>();

        for (int index = 0; index < numberOfSheets; index++) {
            BeanReader<UserDTO> beanReader =
                    sheetReader.getBeanReader(index, UserDTO.class);

            beanReader.setSkipHeader(true);

            try {
                List<UserDTO> sheetUsers = beanReader.read();

                if (sheetUsers.isEmpty()) {
                    continue;
                }

                users.addAll(userService.createUsers(sheetUsers));
            } catch (BeanReaderException ex) {
                throw new FailedImportException("Excel structure is invalid: " + ex.getEntry().toString());
            } catch (Exception ex) {
                throw new FailedImportException("Unable to import data");
            }
        }

        return users;
    }
}
