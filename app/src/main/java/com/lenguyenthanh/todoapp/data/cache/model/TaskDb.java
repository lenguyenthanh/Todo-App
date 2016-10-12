package com.lenguyenthanh.todoapp.data.cache.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

@AutoValue
abstract public class TaskDb implements TaskModel {
    public static final Factory<TaskDb> FACTORY = new Factory<>(new Creator<TaskDb>() {

        @Override
        public TaskDb create(final long _id, @NonNull final long id, @NonNull final String name, final boolean state) {
            return new AutoValue_TaskDb(_id, id, name, state);
        }
    });

    public static final RowMapper<TaskDb> SELECT_ALL_MAPPER = FACTORY.select_AllMapper();

    public static final RowMapper<TaskDb> SELECT_BY_ID_MAPPER = FACTORY.select_By_IdMapper();

    public static final RowMapper<TaskDb> SELECT_PENDING_TASK_MAPPER = FACTORY.select_Pending_TaksMapper();

    public static final RowMapper<TaskDb> SELECT_DONE_TASK_MAPPER = FACTORY.select_Done_TaksMapper();
}
