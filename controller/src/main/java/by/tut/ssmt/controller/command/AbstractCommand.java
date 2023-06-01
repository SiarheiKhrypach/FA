package by.tut.ssmt.controller.command;

import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.dataProcessor.DataProcessorList;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.tut.ssmt.controller.util.ControllerConstants.*;

public class AbstractCommand {


    protected void checkOperationForSuccess(HttpServletRequest request, boolean result) {
        if (result) {
            request.getSession().setAttribute(MESSAGE, "Successful operation");
        } else {
            request.getSession().setAttribute(MESSAGE, "Operation failed");
        }
    }

    protected String calcProportion(DataProcessorList dataProcessorList, List<Product> products){
        final String formattedProportion = dataProcessorList.calculate(products);
        return formattedProportion;
    }

    protected void setAttProportion(ServiceValidator serviceValidator, String formattedProportion, HttpServletRequest request)  {
        serviceValidator.isNotNull(formattedProportion);
        request.getSession().setAttribute(PROPORTION, formattedProportion);
    }


}
