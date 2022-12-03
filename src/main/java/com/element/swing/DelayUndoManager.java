/*
 * @(#)DelayUndoManager.java 12/31/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.element.swing;

import javax.swing.*;
import javax.swing.undo.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An undo manager that aggregates UndoableEdits into one CompoundEdit if they are executed very close to each other. By
 * default, the gap is 500 ms. You can control it by passing in a different number in the constructor.
 */
public class DelayUndoManager extends UndoManager {
	private int _delay = 500;
	private CompoundEdit _cache;
	protected Timer _timer;

	public DelayUndoManager() {
	}

	public DelayUndoManager(int delay) {
		_delay = delay;
	}


	/**
	 * Commits the cached edit.
	 */
	public synchronized void commitCache() {
		if (_cache != null) {
			_cache.end();
			addEditWithoutCaching();
			_cache = null;
		}
	}

	/**
	 * Calls super.addEdit without caching.
	 */
	public void addEditWithoutCaching() {
		DelayUndoManager.super.addEdit(_cache);
	}

	public synchronized void discardCache() {
		_cache = null;
		if (_timer != null) {
			_timer.stop();
			_timer = null;
		}
	}

	@Override
	public synchronized boolean addEdit(UndoableEdit anEdit) {
		if (_cache == null) {
			_cache = new CompoundEdit();
			boolean ret = _cache.addEdit(anEdit);
			if (ret) {
				_timer = new Timer(_delay, e -> commitCache());
				_timer.setRepeats(false);
				_timer.start();
			}
			return ret;
		} else {
			if (_timer != null) {
				_timer.restart();
			}
			return _cache.addEdit(anEdit);
		}
	}

	/**
	 * Override to commit the cache before checking undo status.
	 *
	 * @return true if an undo operation would be successful now, false otherwise
	 */
	@Override
	public synchronized boolean canUndo() {
		commitCache();
		return super.canUndo();
	}

	/**
	 * Override to commit the cache before checking redo status.
	 *
	 * @return true if an redo operation would be successful now, false otherwise
	 */
	@Override
	public synchronized boolean canRedo() {
		commitCache();
		return super.canRedo();
	}

	/**
	 * Override to commit the cache before undo.
	 *
	 * @throws CannotUndoException
	 */
	@Override
	public synchronized void undo() throws CannotUndoException {
		commitCache();
		super.undo();
	}

	/**
	 * Override to commit the cache before redo.
	 *
	 * @throws CannotRedoException
	 */
	@Override
	public synchronized void redo() throws CannotRedoException {
		commitCache();
		super.redo();
	}

	@Override
	public synchronized void discardAllEdits() {
		super.discardAllEdits();
		discardCache();
	}

	/**
	 * Checks if there are pending edits in the DelayUndoManager.
	 *
	 * @return true if there are pending edits. Otherwise false.
	 */
	public boolean isCacheEmpty() {
		return _cache == null;
	}
}
