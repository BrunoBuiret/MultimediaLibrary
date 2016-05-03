package com.polytech.multimedia_library.repositories;

import com.polytech.multimedia_library.AbstractException;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class RepositoryException extends AbstractException
{
    /**
     * {@inheritDoc}
     */
    public RepositoryException(Exception ex, String message)
    {
        super(ex, message);
    }

    /**
     * {@inheritDoc}
     */
    public RepositoryException(Exception ex, String title, String message)
    {
        super(ex, title, message);
    }
}
