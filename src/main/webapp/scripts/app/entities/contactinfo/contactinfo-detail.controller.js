'use strict';

angular.module('tiimspotApp')
    .controller('ContactinfoDetailController', function ($scope, $rootScope, $stateParams, entity, Contactinfo) {
        $scope.contactinfo = entity;
        $scope.load = function (id) {
            Contactinfo.get({id: id}, function(result) {
                $scope.contactinfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('tiimspotApp:contactinfoUpdate', function(event, result) {
            $scope.contactinfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
