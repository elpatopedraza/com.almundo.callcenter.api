package com.almundo.callcenter.api.call.repositories;

import com.almundo.callcenter.api.call.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class that handles all the data logic operations for a call in a H2 database.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public interface CallRepository extends JpaRepository<Call, String>
{

}
