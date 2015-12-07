'use strict';

angular.module('tiimspotApp')
    .factory('Ouder', function ($resource, DateUtils) {
        return $resource('api/ouders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
