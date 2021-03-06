select table_schema||'.'||table_name as full_name, 

(select count(*) from 
information_schema.referential_constraints rc, 
information_schema.key_column_usage cs
    where
    cs.constraint_name = rc.unique_constraint_name
    and cs.table_schema||'.'||cs.table_name = t.table_schema||'.'||t.table_name ) as relations

,
table_catalog,
table_schema,
table_name,

(SELECT pg_relation_size(C.oid)/1024/1024 
  FROM pg_class C
  LEFT JOIN pg_namespace N ON (N.oid = C.relnamespace)
  WHERE nspname || '.' || relname =  t.table_schema||'.'||t.table_name
  ) as size_megabytes,

(SELECT pg_total_relation_size(C.oid)/1024/1024 
  FROM pg_class C
  LEFT JOIN pg_namespace N ON (N.oid = C.relnamespace)
  WHERE nspname || '.' || relname =  t.table_schema||'.'||t.table_name
  ) as total_size_megabytes,

  (SELECT reltuples::bigint
  FROM pg_class C
  LEFT JOIN pg_namespace N ON (N.oid = C.relnamespace)
  WHERE nspname || '.' || relname =  t.table_schema||'.'||t.table_name
  ) as estimate_rows

  from information_schema.tables t where t.table_schema not in ('pg_catalog', 'information_schema', 'public')
AND t.table_schema in ('academico','graduacao','posgraduacao','proex','arex')
 order by 4;


 select
    rc.constraint_name,
    rc.unique_constraint_name,
    rc.match_option,
    rc.update_rule,
    rc.delete_rule,
    cs.table_schema||'.'||cs.table_name as source,
    ct.table_schema||'.'||ct.table_name as target,	
    true as directed
    
    from information_schema.referential_constraints rc,
    information_schema.key_column_usage cs,
    information_schema.key_column_usage ct
    where
    cs.constraint_schema||'.'||cs.constraint_name = rc.constraint_schema||'.'||rc.constraint_name
    and ct.constraint_schema||'.'||ct.constraint_name = rc.unique_constraint_schema||'.'||rc.unique_constraint_name
    and cs.table_schema||'.'||cs.table_name = '$id'	
    and ct.table_schema != 'public'	
