insert into instruments (id, name, underlyingIndex, issuer, dateOfFirstQuotation) VALUES
    (1, 'instrument1', 'index1', 'issuer1', '20200101'),
    (2, 'instrument2', 'index2', 'issuer2', '20200101'),
    (3, 'instrument3', 'index3', 'issuer3', '20200101');

insert into price_records(instrument_id, value, date) VALUES
    (1, 10, '20200101'),
    (1, 20, '20210101'),
    (2, 10, '20200101'),
    (2, 20, '20210101'),
    (3, 10, '20200101'),
    (3, 20, '20210101');