package com.almundo.callcenter.api.test.utils;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.model.EmployeeType;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Class that generates samples of any entity in the system.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public class Samples
{
    /**
     * Creates a random sample employee.
     *
     * @return a sample employee with filled fields.
     */
    public static Employee createSampleEmployee()
    {
        Employee employee = new Employee();

        employee.setAvailable(RandomUtil.getRandomBoolean());
        employee.setEmail(
                StringUtils.join(RandomUtil.generateRandomWords(1, 20, 30), " "));
        employee.setNames(
                StringUtils.join(RandomUtil.generateRandomWords(2, 7, 10), " "));
        employee.setSurnames(
                StringUtils.join(RandomUtil.generateRandomWords(2, 10, 10), " "));
        employee.setType(RandomUtil.getRandomEnum(EmployeeType.class));
        employee.setId(UUID.randomUUID().toString());

        return employee;
    }

    /**
     * Creates a random sample call with an employee attached to it.
     *
     * @param employee the employee to attach to the random sample.
     * @return a sample employee with filled fields.
     */
    public static Call createSampleCall(Employee employee)
    {
        Call call = new Call();

        call.setEmployee(employee);
        call.setDurationInSecs(RandomUtil.getRandomInt(5, 10));
        call.setState(RandomUtil.getRandomEnum(CallState.class));
        call.setId(UUID.randomUUID().toString());

        return call;
    }
}
