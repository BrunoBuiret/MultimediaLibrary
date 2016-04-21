package com.polytech.multimedia_library.editors;

import com.polytech.multimedia_library.repositories.OwnersRepository;
import java.beans.PropertyEditorSupport;
import org.springframework.stereotype.Component;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 * @see http://stackoverflow.com/questions/12875299/spring-mvc-formselect-tag
 */
@Component
public class OwnerEditor extends PropertyEditorSupport
{
    /**
     * 
     */
    protected OwnersRepository ownersRepository = new OwnersRepository();

    /**
     * 
     * @param text
     * @throws IllegalArgumentException 
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        this.setValue(this.ownersRepository.fetch(Integer.parseInt(text)));
    }
}
