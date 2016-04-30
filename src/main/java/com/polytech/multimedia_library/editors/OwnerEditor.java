package com.polytech.multimedia_library.editors;

import com.polytech.multimedia_library.repositories.OwnersRepository;
import com.polytech.multimedia_library.repositories.RepositoryException;
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
     * An instance of the owners' repository to fetch them.
     */
    protected OwnersRepository ownersRepository = new OwnersRepository();

    /**
     * Transforms a string containing an owner's id into an owner instance.
     * 
     * @param text The owner's id.
     * @throws RepositoryException If the owner can't be fetched.
     * @throws NumberFormatException If the string can't be parsed.
     */
    @Override
    public void setAsText(String text)
    throws RepositoryException, NumberFormatException
    {
        this.setValue(this.ownersRepository.fetch(Integer.parseInt(text)));
    }
}
