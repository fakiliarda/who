CREATE TABLE userprofile(
    id UUID NOT NULL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    imagelink VARCHAR(255)
);

CREATE TABLE actions(
    dbid SERIAL NOT NULL PRIMARY KEY,
    userProfileId UUID NOT NULL,
    imagelink VARCHAR(255),
    message VARCHAR(255),
    FOREIGN KEY(userProfileId) REFERENCES userprofile(id)
);