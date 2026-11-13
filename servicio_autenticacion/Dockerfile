
FROM quay.io/keycloak/keycloak:26.8.2 AS builder

ENV KC_DB=postgres

RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.8.2

COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENV KC_HTTP_ENABLED=true
ENV KC_HTTP_HOST=0.0.0.0
ENV KC_HTTP_PORT=8080
ENV KC_PROXY_HEADERS=xforwarded

EXPOSE 8080

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

CMD ["start", "--optimized"]
