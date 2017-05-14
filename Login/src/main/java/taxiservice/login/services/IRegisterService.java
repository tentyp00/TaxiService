package taxiservice.login.services;

import taxiservice.login.dto.RegisterDataDTO;
import taxiservice.login.exceptions.AlreadyExistingEmailException;
import taxiservice.login.exceptions.AlreadyExistingLoginException;

/**
 * Created by bartl on 14.05.2017.
 */
public interface IRegisterService {

    void addClient(RegisterDataDTO registerDataDTO) throws AlreadyExistingLoginException, AlreadyExistingEmailException;
}
