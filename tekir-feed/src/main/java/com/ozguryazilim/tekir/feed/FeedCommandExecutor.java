/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.tekir.feed;

import com.ozguryazilim.tekir.entities.Feed;
import com.ozguryazilim.telve.messagebus.command.AbstractCommandExecuter;
import com.ozguryazilim.telve.messagebus.command.CommandExecutor;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Feed Command Executor
 * 
 * @author Hakan Uygun
 */
@CommandExecutor(command = FeedCommand.class)
public class FeedCommandExecutor extends AbstractCommandExecuter<FeedCommand>{
    
    private static final Logger LOG = LoggerFactory.getLogger(FeedCommandExecutor.class);
    
    @Inject
    private FeedRepository repository;
    
    @Override
    public void execute(FeedCommand command) {
        LOG.debug("Feed Command executed");
        
        Feed feed = new Feed();
        feed.setBasePointer(command.getBasePointer());
        feed.setBody(command.getBody());
        feed.setDate(command.getDate());
        feed.setFeeder(command.getFeeder());
        feed.setRelatedPointer(command.getRelatedPointer());
        feed.setSubject(command.getSubject());
        feed.setType(command.getType());
        feed.setUser(command.getUser());
        
        repository.save(feed);
        
    }
    
}
