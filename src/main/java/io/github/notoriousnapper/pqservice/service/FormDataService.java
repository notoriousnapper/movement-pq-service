package io.github.notoriousnapper.pqservice.service;

import io.github.notoriousnapper.pqservice.meta.IgnoredForFormData;
import io.github.notoriousnapper.pqservice.model.FormData;
import io.github.notoriousnapper.pqservice.model.list.FileItem;
import io.github.notoriousnapper.pqservice.model.list.ListItem;
import io.github.notoriousnapper.pqservice.meta.RequiredForFormData;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

/*
 * Given a fileType, will grab associated FileClass
 * FileClass will then spit back all the required properties needed for input
 *
 */




@Service
public class FormDataService {

  static HashMap<String, Class> map = new HashMap();

  static {
    map.put("listItem", ListItem.class);
    map.put("file", FileItem.class);
  }


  public FormData getFormData(String fileTypeName) {
    Class targetClass = map.get(fileTypeName);
    if (targetClass == null){
      return null;
    }
    else {
      List<String> requiredFormPropertiesList = new ArrayList<>();
      List<String> extraFormPropertiesList = new ArrayList<>();

      Field[] fields = targetClass.getDeclaredFields();
      FormData formData = new FormData();
      formData.setFormDataTitle(targetClass.getSimpleName() + "Form");
      for (Field f : fields){
        System.out.println("field: " + f.getName());
        for (Annotation a : f.getDeclaredAnnotations()){
          System.out.println("Annotations: " + a);
        }
        if (f.isAnnotationPresent(RequiredForFormData.class)){
          requiredFormPropertiesList.add(f.getName());
        } else if (!f.isAnnotationPresent(IgnoredForFormData.class)){
          extraFormPropertiesList.add(f.getName());
        }
      }
      formData.setRequiredFormProperties(requiredFormPropertiesList);
      formData.setExtraFormProperties(extraFormPropertiesList);
      return formData;
    }

  }
}
