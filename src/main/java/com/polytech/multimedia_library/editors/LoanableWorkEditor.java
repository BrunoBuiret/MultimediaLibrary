package com.polytech.multimedia_library.editors;

import com.polytech.multimedia_library.repositories.RepositoryException;
import com.polytech.multimedia_library.repositories.works.LoanableWorksRepository;
import java.beans.PropertyEditorSupport;
import org.springframework.stereotype.Component;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 * @see http://stackoverflow.com/questions/12875299/spring-mvc-formselect-tag
 */
@Component
public class LoanableWorkEditor extends PropertyEditorSupport
{
    /**
     * An instance of the loanable works' repository to fetch them.
     */
    protected LoanableWorksRepository worksRepository = new LoanableWorksRepository();

    /**
     * Transforms a string containing a loanable work's id into a loanable work instance.
     *
     * @param text The loanable work's id.
     * @throws RepositoryException If the loanable work can't be fetched.
     * @throws NumberFormatException If the string can't be parsed.
     */
    @Override
    public void setAsText(String text)
    throws RepositoryException, NumberFormatException
    {
        this.setValue(this.worksRepository.fetch(Integer.parseInt(text)));
    }
}
