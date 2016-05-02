package com.polytech.multimedia_library.editors;

import com.polytech.multimedia_library.repositories.AdherentsRepository;
import com.polytech.multimedia_library.repositories.RepositoryException;
import java.beans.PropertyEditorSupport;
import org.springframework.stereotype.Component;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 * @see http://stackoverflow.com/questions/12875299/spring-mvc-formselect-tag
 */
@Component
public class AdherentEditor extends PropertyEditorSupport
{
    /**
     * An instance of the owners' repository to fetch them.
     */
    protected AdherentsRepository adherentsRepository = new AdherentsRepository();

    /**
     * Transforms a string containing an adherent's id into an adherent instance.
     * 
     * @param text The adherent's id.
     * @throws RepositoryException If the adherent can't be fetched.
     * @throws NumberFormatException If the string can't be parsed.
     */
    @Override
    public void setAsText(String text)
    throws RepositoryException, NumberFormatException
    {
        this.setValue(this.adherentsRepository.fetch(Integer.parseInt(text)));
    }
}
