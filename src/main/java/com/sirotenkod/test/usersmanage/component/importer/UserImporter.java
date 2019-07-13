package com.sirotenkod.test.usersmanage.component.importer;

import com.sirotenkod.test.usersmanage.dao.UserDAO;

import java.io.InputStream;
import java.util.List;

public interface UserImporter {
    List<UserDAO> importFromSheet(InputStream inputStream);
}
