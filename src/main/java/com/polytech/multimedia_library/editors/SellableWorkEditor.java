package com.polytech.multimedia_library.editors;

import com.polytech.multimedia_library.repositories.RepositoryException;
import com.polytech.multimedia_library.repositories.works.SellableWorksRepository;
import java.beans.PropertyEditorSupport;
import org.springframework.stereotype.Component;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 * @see http://stackoverflow.com/questions/12875299/spring-mvc-formselect-tag
 */
@Component
public class SellableWorkEditor extends PropertyEditorSupport
{
    /**
     * An instance of the sellable works' repository to fetch them.
     */
    protected SellableWorksRepository worksRepository = new SellableWorksRepository();

    /**
     * Transforms a string containing a sellable work's id into a sellable work instance.
     *
     * @param text The sellable work's id.
     * @throws RepositoryException If the sellable work can't be fetched.
     * @throws NumberFormatException If the string can't be parsed.
     */
    @Override
    public void setAsText(String text)
    throws RepositoryException, NumberFormatException
    {
        this.setValue(this.worksRepository.fetch(Integer.parseInt(text)));
    }
}
