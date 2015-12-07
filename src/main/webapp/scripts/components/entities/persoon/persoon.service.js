'use strict';

angular.module('tiimspotApp')
    .factory('Persoon', function ($resource, DateUtils) {
        return $resource('api/persoons/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.geboorteDatum = DateUtils.convertDateTimeFromServer(data.geboorteDatum);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
