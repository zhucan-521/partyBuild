package com.egovchina.partybuilding.partybuild.config;

import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNoticeDept;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class MsgNoticeEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MsgNoticeEvent(Object source) {
        super(source);
    }

    public MsgNoticeEvent(List<TabPbMsgNoticeDept> innerSource) {
        super(innerSource);
        this.innerSource = innerSource;
    }

    private List<TabPbMsgNoticeDept> innerSource;

    public List<TabPbMsgNoticeDept> getInnerSource() {
        return innerSource;
    }

    public void setInnerSource(List<TabPbMsgNoticeDept> innerSource) {
        this.innerSource = innerSource;
    }
}
