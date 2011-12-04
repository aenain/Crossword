/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import crossword.Settings;

/**
 *
 * @author arturhebda
 */
public class BaseController {
    protected Settings settings;

    public BaseController(Settings settings) {
        this.settings = settings;
    }

    public Settings getSettings() { return settings; }
}
